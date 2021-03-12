package com.honghao.cloud.accountapi.service.impl;

import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import com.honghao.cloud.accountapi.domain.mapper.WaybillBcListMapper;
import com.honghao.cloud.accountapi.service.RedissonService;
import com.honghao.cloud.basic.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redisson服务
 *
 * @author chenhonghao
 * @date 2020-12-21 15:33
 */
@Slf4j
@Service
public class RedissonServiceImpl implements RedissonService {
    private static final Object OBJECT = new Object();
    @Resource
    private Redisson redisson;
    @Resource
    private WaybillBcListMapper waybillBcListMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public BaseResponse redissonLock(String userId) {
        RLock lock = redisson.getLock(userId);
        try {
            lock.lock();
            return BaseResponse.success();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public BaseResponse redissonBloomFilter(String userId) {
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("bloomFilter");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
        bloomFilter.tryInit(100000000L,0.03);
        bloomFilter.add("1");
        bloomFilter.add("2");
        bloomFilter.add("3");
        bloomFilter.add("4");
        System.out.println(bloomFilter.contains("5"));
        return null;
    }

    /**
     * redis和mysql双写一致性
     * @param userId userId
     * @return BaseResponse
     */
    @Override
    public BaseResponse redissonUpdate(String userId) {
        RReadWriteLock test = redisson.getReadWriteLock("test");
        try {
            test.readLock().lock(1, TimeUnit.SECONDS);
            boolean b = test.writeLock().tryLock();
            if (b){
                waybillBcListMapper.updateByPrimaryKey(new WaybillBcList());
                redisTemplate.delete(userId);
            }
        } finally {
            test.writeLock().unlock();
        }
        return null;
    }

    @Override
    public BaseResponse redissonQuery(String userId) {
        RReadWriteLock test = redisson.getReadWriteLock("test");
        try {
            boolean b = test.readLock().tryLock();
            if (b){
                Object o = redisTemplate.opsForValue().get(userId);
                if (o==null){
                    synchronized (OBJECT){
                        o = redisTemplate.opsForValue().get(userId);
                        if (o==null){
                            o = waybillBcListMapper.selectByPrimaryKey(userId);
                            redisTemplate.opsForValue().set(userId,o);
                        }
                    }
                }
                return BaseResponse.successData(o);
            }
            return BaseResponse.error();
        } finally {
            test.readLock().unlock();
        }
    }
}
