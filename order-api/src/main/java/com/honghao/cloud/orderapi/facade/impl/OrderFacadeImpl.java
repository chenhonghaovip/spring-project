package com.honghao.cloud.orderapi.facade.impl;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.orderapi.facade.OrderFacade;
import com.honghao.cloud.orderapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单操作实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:28
 */
@Slf4j
@Service
public class OrderFacadeImpl implements OrderFacade {
    private static final String DICTIONARY="dictionart_";

    @Resource
    private OrderService orderService;


    @Override
    public Boolean createUser(String data) {
        log.info("dingdanfuwukaidsaf>>>>>>>>>>>>>>>>");
        log.info(data);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("value","chenwenliang");
        orderService.createUser();
        return null;
    }

    public Boolean createUserFallback(String data){

        log.info("服务熔断，返回默认值！");
        return false;
    }
}