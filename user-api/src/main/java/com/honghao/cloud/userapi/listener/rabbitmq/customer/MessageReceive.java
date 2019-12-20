package com.honghao.cloud.userapi.listener.rabbitmq.customer;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.config.RabbitConfig;
import com.honghao.cloud.userapi.config.ThreadPoolInitConfig;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.entity.WaybillBcListDetail;
import com.honghao.cloud.userapi.domain.entity.WaybillBcListDetailAction;
import com.honghao.cloud.userapi.domain.mapper.master.WaybillBcListDetailActionMapper;
import com.honghao.cloud.userapi.domain.mapper.master.WaybillBcListDetailMapper;
import com.honghao.cloud.userapi.domain.mapper.master.WaybillBcListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenhonghao
 * @date 2019-11-18 10:18
 */
@Slf4j
@Component
public class MessageReceive {
    private AtomicLong atomicLong = new AtomicLong(2017111800000001L);

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();
    @Resource
    private WaybillBcListMapper waybillBcListMapper;
    @Resource
    private WaybillBcListDetailActionMapper waybillBcListDetailActionMapper;
    @Resource
    private WaybillBcListDetailMapper waybillBcListDetailMapper;

    @RabbitListener(queues = RabbitConfig.TEST_1)
    private void consumptionQueue(String data){
        log.info("队列处理：{}",data);
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolInitConfig.build("test");
        threadPoolExecutor.execute(() -> {

            WaybillBcList waybillBcList = JSON.parseObject(data,WaybillBcList.class);
            Date date = waybillBcList.getOrderTime();
            String wId = String.valueOf(atomicLong.getAndIncrement());
            waybillBcList.setWId(wId);
            waybillBcList.setBatchId(String.valueOf(atomicLong.getAndIncrement()));
            waybillBcList.setCreateDate(date);
            waybillBcList.setCreateTime(date);
            waybillBcList.setUpdateTime(date);
            waybillBcListMapper.insertSelective(waybillBcList);

            WaybillBcListDetailAction waybillBcListDetailAction = JSON.parseObject(data,WaybillBcListDetailAction.class);
            waybillBcListDetailAction.setwId(wId);
            waybillBcListDetailAction.setCreateTime(date);
            waybillBcListDetailAction.setUpdateTime(date);
            waybillBcListDetailActionMapper.insertSelective(waybillBcListDetailAction);

            Integer orderStatus = waybillBcList.getOrderStatus();
            List<WaybillBcListDetail> listDetails = new ArrayList<>();

            WaybillBcListDetail waybillBcListDetail;
            for (int i = 1; i <= 9; i++) {
                waybillBcListDetail = JSON.parseObject(data,WaybillBcListDetail.class);
                waybillBcListDetail.setwId(wId);
                waybillBcListDetail.setKappAction(i);
                waybillBcListDetail.setLatitude(waybillBcList.getReceiveLatitude());
                waybillBcListDetail.setLongitude(waybillBcList.getReceiveLongitude());
                waybillBcListDetail.setId(String.valueOf(atomicLong.getAndIncrement()));
                waybillBcListDetail.setCreateDate(date);
                waybillBcListDetail.setCreateTime(date);
                listDetails.add(waybillBcListDetail);
            }

            List<WaybillBcListDetail> insertList ;
            switch (orderStatus){
                case 1:
                case 4:
                    insertList = Collections.singletonList(listDetails.get(0));
                    break;
                case 2:
                case 5:
                    insertList = Arrays.asList(listDetails.get(0),listDetails.get(2),listDetails.get(3));
                    break;
                case 3:
                    insertList = Arrays.asList(listDetails.get(0),listDetails.get(2),listDetails.get(3),listDetails.get(6));
                    break;
                default:
                    insertList = Collections.emptyList();
            }
            waybillBcListDetailMapper.insertBatch(insertList);
        });
    }
}
