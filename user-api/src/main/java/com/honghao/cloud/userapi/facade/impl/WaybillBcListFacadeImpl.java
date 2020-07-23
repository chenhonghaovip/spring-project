package com.honghao.cloud.userapi.facade.impl;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.userapi.client.OrderClient;
import com.honghao.cloud.userapi.config.ParamConfig;
import com.honghao.cloud.userapi.domain.entity.CloudDeliveryMan;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.dto.request.EventDTO;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.listener.event.EventDemo;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import com.honghao.cloud.userapi.service.CloudOrderService;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import com.honghao.cloud.userapi.task.AsyncTask;
import com.honghao.cloud.userapi.utils.JedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 订单操作实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:28
 */
@Slf4j
@Service
public class WaybillBcListFacadeImpl implements WaybillBcListFacade {
    @Resource
    private AsyncTask asyncTask;
    @Resource
    private OrderClient orderClient;
    @Resource
    private ParamConfig paramConfig;
    @Resource
    private JedisOperator jedisOperator;
    @Resource
    private MessageSender messageSender;
    @Resource
    private CloudOrderService cloudOrderService;
    @Resource
    private WaybillBcListService waybillBcListService;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse dateSource() {
        EventDTO eventDTO= EventDTO.builder().code(1)
                .desc("chenhonghao").build();
        EventDemo eventListener=new EventDemo(this,eventDTO);
        applicationEventPublisher.publishEvent(eventListener);

        WaybillBcList waybillBcList = WaybillBcList.builder().wId("2017111800000018").deleteFlag(1).build();
        waybillBcListService.updateInfos(waybillBcList);

        CloudDeliveryMan cloudDeliveryMan =CloudDeliveryMan.builder().deliveryManId("3704784038811670").deleteFlag(1).build();
        cloudOrderService.updateInfos(cloudDeliveryMan);
        return BaseResponse.success();
    }
}
