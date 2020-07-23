package com.honghao.cloud.accountapi.service.impl;


import com.alibaba.fastjson.JSON;
import com.honghao.cloud.accountapi.common.enums.ErrorCodeEnum;
import com.honghao.cloud.accountapi.domain.entity.Order;
import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import com.honghao.cloud.accountapi.domain.mapper.OrderMapper;
import com.honghao.cloud.accountapi.service.AccountService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单服务实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:32
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    private ConcurrentHashMap<String, ReentrantLock> concurrentHashMap = new ConcurrentHashMap<>();
    @Resource
    private Redisson redisson;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrders(String data) {
        stringRedisTemplate.opsForValue().set("123","123");
        Order order = JSON.parseObject(data,Order.class);
        order.setBatchId("111111");
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public BaseResponse cacheBreakdown(String userId) {
        Object o = stringRedisTemplate.opsForValue().get(userId);
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
            Object temp = stringRedisTemplate.opsForValue().get(userId);
            if (Objects.nonNull(temp)){
                return BaseResponse.success();
            }
            // 查询数据库并且放入到缓存中
            Order order = orderMapper.selectByPrimaryKey(userId);
            stringRedisTemplate.opsForValue().set(userId,JSON.toJSONString(order));
            return BaseResponse.success();
        } finally {
            // 当前锁的等待队列为空时，删除该锁
            if (!reentrantLock.hasQueuedThreads()){
                concurrentHashMap.remove(userId);
            }
            reentrantLock.unlock();
        }
    }

    @Override
    public BaseResponse cachePenetration(String userId) {
        return BaseResponse.success();
    }

    @Override
    public BaseResponse redisLcok(String userId) {
        String clientId = UUID.randomUUID().toString();
        try {
            Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(userId, clientId, 10, TimeUnit.SECONDS);
            if (!aBoolean){
                return BaseResponse.error(ErrorCodeEnum.API_GATEWAY_ERROR);
            }

            // 业务处理
        } finally {
            if (clientId.equals(stringRedisTemplate.opsForValue().get(userId))){
                stringRedisTemplate.delete(userId);
            }
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse redissonLcok(String userId) {
        RLock lock = redisson.getLock(userId);
        lock.lock(10,TimeUnit.SECONDS);

        try {
            return BaseResponse.success();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public BaseResponse redisList(String userId) {
        List<WaybillBcList> waybillBcLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            waybillBcLists.add(WaybillBcList.builder().batchId(userId+i).build());
        }
        redisTemplate.delete(userId);
        redisTemplate.opsForList().leftPushAll(userId,waybillBcLists.toArray());
        return BaseResponse.successData(redisTemplate.opsForList().range(userId,0,-1));
    }
}
