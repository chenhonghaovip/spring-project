package com.honghao.cloud.orderapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.orderapi.domain.entity.Order;
import com.honghao.cloud.orderapi.domain.mapper.OrderMapper;
import com.honghao.cloud.orderapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单服务实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:32
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public void createOrders(String data) {
        Order order = JSON.parseObject(data,Order.class);
        orderMapper.insert(order);
    }
}
