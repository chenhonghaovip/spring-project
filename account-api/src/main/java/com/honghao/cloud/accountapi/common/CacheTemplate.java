package com.honghao.cloud.accountapi.common;

import com.honghao.cloud.accountapi.common.enums.ErrorCodeEnum;
import com.honghao.cloud.accountapi.common.factory.CacheLoad;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author chenhonghao
 * @date 2020-07-25 16:14
 */
@Component
public class CacheTemplate<T> {
    @Resource
    private Redisson redisson;
    @Resource
    private RedisBloomFilter redisBloomFilter;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    public BaseResponse redisFindCache(String key, long expire, TimeUnit timeUnit, CacheLoad<T> cacheLoad, boolean filter,String bloomKey){
        // 布隆过滤器校验
        if (filter){
            boolean b = redisBloomFilter.get(bloomKey, key);
            if (!b){
                return BaseResponse.error(ErrorCodeEnum.DATA_DOES_NOT_EXIST);
            }
        }

        // 双锁检查机制，判断是否缓存中存在，不存在时，查询数据库并且缓存
        Object o = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(o)){
            return BaseResponse.successData(o);
        }
        RLock lock = redisson.getLock(key);
        lock.lock();

        try {
            if (Objects.nonNull(o = redisTemplate.opsForValue().get(key))){
                return BaseResponse.successData(o);
            }
            o = cacheLoad.run();
            if (Objects.nonNull(o)){
                if (expire>0){
                    redisTemplate.opsForValue().setIfAbsent(key,o,expire,timeUnit);
                }else {
                    redisTemplate.opsForValue().setIfAbsent(key,o);
                }
                return BaseResponse.successData(o);
            }
            return BaseResponse.error(ErrorCodeEnum.DATA_DOES_NOT_EXIST);
        } finally {
            lock.unlock();
        }
    }
}
