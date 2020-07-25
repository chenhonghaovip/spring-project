package com.honghao.cloud.accountapi.common;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.honghao.cloud.accountapi.common.enums.ErrorCodeEnum;
import com.honghao.cloud.accountapi.common.factory.CacheLoad;
import com.honghao.cloud.accountapi.config.ApolloConfig;
import com.honghao.cloud.accountapi.dto.common.Dict;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.utils.BloomFilterHelper;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author chenhonghao
 * @date 2020-07-25 16:14
 */
@Component
public class CacheTemplate<T> {
    private BloomFilterHelper<String> bloomFilterHelper;
    @Resource
    private Redisson redisson;
    @Resource
    private ApolloConfig apolloConfig;
    @Resource
    private RedisBloomFilter redisBloomFilter;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void init(){
        this.bloomFilterHelper= new BloomFilterHelper<>((Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8)
                .putString(from, Charsets.UTF_8), apolloConfig.getExpectedInsertions(), apolloConfig.getFpp());
    }


    public BaseResponse redisStringCache(String prefix,String key, CacheLoad<T> cacheLoad,String bloomKey){
        return this.redisStringCache(prefix,key,-1,null,cacheLoad,bloomKey);
    }

    public BaseResponse redisStringCache(String prefix,String key, CacheLoad<T> cacheLoad){
        return this.redisStringCache(prefix,key,-1,null,cacheLoad,null);
    }

    public BaseResponse redisStringCache(String prefix,String key, long expire, TimeUnit timeUnit, CacheLoad<T> cacheLoad){
        return this.redisStringCache(prefix,key,expire,timeUnit,cacheLoad,null);
    }

    /**
     * redis查询模板（string类型）
     * @param prefix 前缀
     * @param businessId 业务主键
     * @param expire 过期时间
     * @param timeUnit 过期时间单位
     * @param cacheLoad 查询语句
     * @param bloomKey 布隆过滤器的key值
     * @return BaseResponse
     */
    public BaseResponse redisStringCache(String prefix,String businessId, long expire, TimeUnit timeUnit, CacheLoad<T> cacheLoad, String bloomKey){
        // 布隆过滤器校验
        if (StringUtils.isNotBlank(bloomKey)){
            boolean b = redisBloomFilter.includeByBloomFilter(bloomFilterHelper,bloomKey, businessId);
            if (!b){
                return BaseResponse.error(ErrorCodeEnum.DATA_DOES_NOT_EXIST);
            }
        }

        String key = prefix + businessId;
        // 双锁检查机制，判断是否缓存中存在，不存在时，查询数据库并且缓存
        Object o = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(o)){
            return BaseResponse.successData(o);
        }
        RLock lock = redisson.getLock(Dict.R_LOCK + key);
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

    public BaseResponse redisHashCache(String key,String filed, CacheLoad<T> cacheLoad){
        return this.redisHashCache(key,filed,cacheLoad,null);
    }
    /**
     * redis查询模板（hash类型）
     * @param key key
     * @param cacheLoad 查询语句
     * @param bloomKey 布隆过滤器的key值
     * @return BaseResponse
     */
    public BaseResponse redisHashCache(String key,String filed, CacheLoad<T> cacheLoad, String bloomKey){
        // 布隆过滤器校验
        if (StringUtils.isNotBlank(bloomKey)){
            boolean b = redisBloomFilter.includeByBloomFilter(bloomFilterHelper,bloomKey, filed);
            if (!b){
                return BaseResponse.error(ErrorCodeEnum.DATA_DOES_NOT_EXIST);
            }
        }

        // 双锁检查机制，判断是否缓存中存在，不存在时，查询数据库并且缓存
        Object o = redisTemplate.opsForHash().get(key,filed);
        if (Objects.nonNull(o)){
            return BaseResponse.successData(o);
        }
        RLock lock = redisson.getLock(Dict.R_LOCK + key);
        lock.lock();

        try {
            if (Objects.nonNull(o = redisTemplate.opsForHash().get(key,filed))){
                return BaseResponse.successData(o);
            }
            o = cacheLoad.run();
            if (Objects.nonNull(o)){
                redisTemplate.opsForHash().putIfAbsent(key,filed,o);
                return BaseResponse.successData(o);
            }
            return BaseResponse.error(ErrorCodeEnum.DATA_DOES_NOT_EXIST);
        } finally {
            lock.unlock();
        }
    }
}
