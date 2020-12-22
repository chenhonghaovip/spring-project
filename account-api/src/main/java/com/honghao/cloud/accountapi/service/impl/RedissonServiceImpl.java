package com.honghao.cloud.accountapi.service.impl;

import com.honghao.cloud.accountapi.service.RedissonService;
import com.honghao.cloud.basic.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * redisson服务
 *
 * @author chenhonghao
 * @date 2020-12-21 15:33
 */
@Slf4j
@Service
public class RedissonServiceImpl implements RedissonService {
    @Resource
    private Redisson redisson;


    @Override
    public BaseResponse redissonLock(String userId) {
        RLock lock = redisson.getLock(userId);
        RedissonRedLock redissonRedLock = new RedissonRedLock(lock);
        try {
            lock.lock();
            return BaseResponse.success();
        } finally {
            lock.unlock();
        }
    }
}
