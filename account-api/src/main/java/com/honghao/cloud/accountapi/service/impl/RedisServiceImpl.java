package com.honghao.cloud.accountapi.service.impl;

import com.honghao.cloud.accountapi.common.enums.ErrorCodeEnum;
import com.honghao.cloud.accountapi.domain.entity.Order;
import com.honghao.cloud.accountapi.domain.mapper.OrderMapper;
import com.honghao.cloud.accountapi.dto.common.CommonDTO;
import com.honghao.cloud.accountapi.service.RedisService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reids各个数据测试
 *
 * @author chenhonghao
 * @date 2020-07-23 21:31
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    private ConcurrentHashMap<String, ReentrantLock> concurrentHashMap = new ConcurrentHashMap<>();
    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1);
    @Resource
    private Redisson redisson;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @PostConstruct
    public void timerTask(){
        SCHEDULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(()->{

        },0,2,TimeUnit.MINUTES);
    }

    @Override
    public BaseResponse cacheBreakdown(String userId) {
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
            redisTemplate.opsForValue().set(userId, order);
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
    public BaseResponse redisLock(String userId) {
        String clientId = UUID.randomUUID().toString();
        try {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(userId, clientId, 10, TimeUnit.SECONDS);
            if (!aBoolean){
                return BaseResponse.error(ErrorCodeEnum.API_GATEWAY_ERROR);
            }

            // 业务处理
        } finally {
            if (clientId.equals(redisTemplate.opsForValue().get(userId))){
                redisTemplate.delete(userId);
            }
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse redissonLock(String userId) {
        RLock lock = redisson.getLock(userId);
        lock.lock(10, TimeUnit.SECONDS);

        try {
            return BaseResponse.success();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public BaseResponse redisList(String userId) {
        String key = "list"+userId;
        List<Object> ids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ids.add(CommonDTO.builder().code(i).desc(String.valueOf(i)).build());
        }
        redisTemplate.delete(key);
        // 从左边向list中插入数据
        redisTemplate.opsForList().leftPushAll(key,ids);

        // 取出list中的全部数据
        List<Object> range = redisTemplate.opsForList().range(key, 0, -1);


        return BaseResponse.successData(range);
    }

    @Override
    public BaseResponse redisSet(String userId) {
        redisTemplate.opsForSet().add("set"+userId,userId);
        return null;
    }

    @Override
    public BaseResponse redisZSet(String userId) {
        redisTemplate.opsForSet().add("zset"+userId,userId);
        return null;
    }

}
