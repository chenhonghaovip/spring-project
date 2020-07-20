package com.honghao.cloud.accountapi.service.impl;


import com.alibaba.fastjson.JSON;
import com.honghao.cloud.accountapi.base.BaseResponse;
import com.honghao.cloud.accountapi.domain.entity.Order;
import com.honghao.cloud.accountapi.domain.mapper.OrderMapper;
import com.honghao.cloud.accountapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单服务实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:32
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private ConcurrentHashMap<String, ReentrantLock> concurrentHashMap = new ConcurrentHashMap<>();
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrders(String data) {
        Order order = JSON.parseObject(data,Order.class);
        order.setBatchId("111111");
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public BaseResponse redisTest(String userId) {
        Object o = redisTemplate.opsForValue().get(userId);
        if (Objects.nonNull(o)){
            return BaseResponse.success();
        }
        // 缓存未命中时，以用户id为颗粒度加锁，加本地锁，减少访问数据库的次数（如要完全限制只查询一次数据库，使用分布式锁）
        ReentrantLock reentrantLock = new ReentrantLock();
        if ((concurrentHashMap.putIfAbsent(userId,reentrantLock)) != null){
            reentrantLock = concurrentHashMap.get(userId);
        }

        reentrantLock.lock();
        try {
            // 利用DCL(Double Check Lock)双锁检查机制
            Object temp = redisTemplate.opsForValue().get(userId);
            if (Objects.nonNull(temp)){
                return BaseResponse.success();
            }
            // 查询数据库并且放入到缓存中
            Order order = orderMapper.selectByPrimaryKey(userId);
            redisTemplate.opsForValue().set(userId,order);
            return BaseResponse.success();
        } finally {
            // 当前锁的等待队列为空时，删除该锁
            if (!reentrantLock.hasQueuedThreads()){
                concurrentHashMap.remove(userId);
            }
            reentrantLock.unlock();
        }


    }
}
