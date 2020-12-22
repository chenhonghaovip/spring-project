package com.honghao.cloud.basic.common.bean;

import com.google.common.base.Preconditions;
import com.honghao.cloud.basic.common.utils.BloomFilterHelper;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * redis布隆过滤器
 *
 * @author chenhonghao
 * @date 2020-07-25 16:40
 */
public class RedisBloomFilter {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisBloomFilter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据给定的布隆过滤器添加值
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            Boolean bit = redisTemplate.opsForValue().getBit(key, i);
            if (Objects.isNull(bit) || !bit) {
                return false;
            }
        }
        return true;
    }
}
