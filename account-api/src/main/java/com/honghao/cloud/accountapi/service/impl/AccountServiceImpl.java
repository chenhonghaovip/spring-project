package com.honghao.cloud.accountapi.service.impl;


import com.alibaba.fastjson.JSON;
import com.honghao.cloud.accountapi.domain.entity.Order;
import com.honghao.cloud.accountapi.domain.mapper.OrderMapper;
import com.honghao.cloud.accountapi.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
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
public class AccountServiceImpl implements AccountService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrders(String data) {
        stringRedisTemplate.opsForValue().set("123","123");
        Order order = JSON.parseObject(data,Order.class);
        order.setBatchId("111111");
        orderMapper.updateByPrimaryKeySelective(order);
    }


}
