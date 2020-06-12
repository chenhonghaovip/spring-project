package com.honghao.cloud.accountapi.service.impl;


import com.alibaba.fastjson.JSON;
import com.honghao.cloud.accountapi.domain.entity.Order;
import com.honghao.cloud.accountapi.domain.mapper.OrderMapper;
import com.honghao.cloud.accountapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public void createOrders(String data) {
        Order order = JSON.parseObject(data,Order.class);
        orderMapper.insert(order);
    }
}