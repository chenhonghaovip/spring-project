package com.honghao.cloud.userapi.listener.rabbitmq.customer;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.config.RabbitConfig;
import com.honghao.cloud.userapi.config.ThreadPoolInitConfig;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.entity.WaybillBcListDetailAction;
import com.honghao.cloud.userapi.domain.mapper.WaybillBcListDetailActionMapper;
import com.honghao.cloud.userapi.domain.mapper.WaybillBcListMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenhonghao
 * @date 2019-11-18 10:18
 */
@Component
public class MessageReceive {
    @Resource
    private WaybillBcListMapper waybillBcListMapper;
    @Resource
    private WaybillBcListDetailActionMapper waybillBcListDetailActionMapper;

    @RabbitListener(queues = RabbitConfig.TEST_1)
    private void consumptionQueue(String data){
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolInitConfig.build("test");
        threadPoolExecutor.execute(() -> {
            WaybillBcList waybillBcList = JSON.parseObject(data,WaybillBcList.class);
            waybillBcListMapper.insertSelective(waybillBcList);


            WaybillBcListDetailAction waybillBcListDetailAction = JSON.parseObject(data,WaybillBcListDetailAction.class);
            waybillBcListDetailActionMapper.insertSelective(waybillBcListDetailAction);
        });
    }
}
