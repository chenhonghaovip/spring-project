package com.honghao.cloud.orderapi.facade.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import com.honghao.cloud.basic.common.utils.HttpUtil;
import com.honghao.cloud.orderapi.common.dict.Dict;
import com.honghao.cloud.orderapi.config.RabbitConfig;
import com.honghao.cloud.orderapi.domain.entity.Order;
import com.honghao.cloud.orderapi.dto.common.BatchMsgInfoDTO;
import com.honghao.cloud.orderapi.dto.common.MsgInfoDTO;
import com.honghao.cloud.orderapi.dto.request.MsgDTO;
import com.honghao.cloud.orderapi.facade.OrderFacade;
import com.honghao.cloud.orderapi.service.OrderService;
import com.honghao.cloud.orderapi.template.RabbitTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 订单操作实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:28
 */
@Slf4j
@Service
public class OrderFacadeImpl implements OrderFacade {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(1000,2000,"create_order");

    @Resource
    private OrderService orderService;
    @Resource
    private RabbitTemplateService rabbitTemplateService;


    @Override
    public BaseResponse createOrders(String data) {
//        String s = HttpUtil.doPost("http://127.0.0.1:8102/client/deliveryController/getInfo", 10);
//        List<Order> lists = JSONArray.parseArray(s, Order.class);
//        lists.forEach(each->{
//            MsgInfoDTO msgInfoDTO = MsgInfoDTO.builder().businessId(each.getwId()).content(JSON.toJSONString(each))
//                    .status(0).topic(RabbitConfig.CREATE_ORDER).appId(Dict.SERVICE_NAME).url("/order/batchQuery").build();
//
//           threadPoolExecutor.execute(()-> rabbitTemplateService.sendMessage(msgInfoDTO, () -> orderService.createOrders(each)));
//        });
        test();
         return BaseResponse.success();
//      return rabbitTemplateService.sendMessage(msgInfoDTO, () -> orderService.createOrders(order));
    }

    void test(){
        CyclicBarrier cyclicBarrier =new CyclicBarrier(1000);
        String s = HttpUtil.doPost("http://127.0.0.1:8102/client/deliveryController/getInfo", 10);
        List<Order> lists = JSONArray.parseArray(s, Order.class);
        Order each = lists.get(0);
        for (int i = 0; i < 1000; i++) {

            Order or  =new Order();
            BeanUtils.copyProperties(each,or);
            or.setwId(String.valueOf(i));
            MsgInfoDTO msgInfoDTO = MsgInfoDTO.builder().businessId(or.getwId()).content(JSON.toJSONString(or))
                    .status(0).topic(RabbitConfig.CREATE_ORDER).appId(Dict.SERVICE_NAME).url("/order/batchQuery").build();
            threadPoolExecutor.execute(()-> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                rabbitTemplateService.sendMessage(msgInfoDTO, () -> orderService.createOrders(or),1);
            });
        }


    }

    @Override
    public BaseResponse createBatchOrder(String data) {
        String s = HttpUtil.doPost("http://127.0.0.1:8102/client/deliveryController/getInfo", 10);
        List<Order> lists = JSONArray.parseArray(s, Order.class);

        List<MsgInfoDTO> list = new ArrayList<>();
        Order order;
        MsgInfoDTO test1 = MsgInfoDTO.builder().content(JSON.toJSONString(order = lists.get(0)))
                .status(0).topic(RabbitConfig.CREATE_ORDER).businessId(order.getwId()).appId(Dict.SERVICE_NAME).url("/order/batchQuery").build();

        MsgInfoDTO test2 = MsgInfoDTO.builder().content(JSON.toJSONString(order = lists.get(1)))
                .status(0).topic(RabbitConfig.CREATE_ORDER_1).businessId(order.getwId()).appId(Dict.SERVICE_NAME).url("/order/batchQuery").build();

        list.add(test1);
        list.add(test2);
        BatchMsgInfoDTO batchMsgInfoDTO= new BatchMsgInfoDTO();
        batchMsgInfoDTO.setMsgList(list);

        final List<Order> orders = lists.subList(0, 2);
//        return rabbitTemplateService.batchMessage(batchMsgInfoDTO,()->orderService.createBatchOrders(orders));
        return rabbitTemplateService.batchMessage(batchMsgInfoDTO,()->orderService.createBatchOrders(orders),1);
    }

    @Override
    public List<Long> batchQuery(List<MsgDTO> list) {
        log.info("batchQuery:{}",list);
        List<String> collect = list.stream().map(MsgDTO::getBusinessId).collect(Collectors.toList());
        List<String> wIds = orderService.batchQuery(collect);
        return list.stream().filter(each->wIds.contains(each.getBusinessId())).map(MsgDTO::getMsgId).collect(Collectors.toList());
    }

    @Override
    public BaseResponse update(String wId) {
        return orderService.update(wId);
    }
}
