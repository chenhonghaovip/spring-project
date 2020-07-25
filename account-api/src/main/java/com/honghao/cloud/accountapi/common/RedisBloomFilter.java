package com.honghao.cloud.accountapi.common;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redis布隆过滤器
 *
 * @author chenhonghao
 * @date 2020-07-25 16:40
 */
@Data
@Component
@ConfigurationProperties(value = "redis.bloom.filter")
public class RedisBloomFilter<T> {
    /**
     * 预计插入量
     */
    private long expectedInsertions;

    /**
     * 错误率
     */
    private double fpp;

    private Funnel<T> funnel;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 向布隆过滤器中添加数据
     * @param key key
     * @param value value
     */
    public void put(String key,String value){
        long[] index = getIndex(value);
        for (long l : index) {
            redisTemplate.opsForValue().setBit(key,l,true);
        }
    }

    /**
     * 判断value在key中是否存在
     * @param key key (存储布隆过滤器的key)
     * @param value value (想要查询的值，也就是缓存中的key,布隆过滤器中的value)
     * @return boolean
     */
    public boolean get(String key,String value){
        long[] index = getIndex(value);
        for (long l : index) {
            Boolean bit = redisTemplate.opsForValue().getBit(key, l);
            if (!bit) {
                return false;
            }
        }
        return true;
    }

    /**
     * 布隆过滤器算法
     * @param value value
     * @return long[]
     */
    private long[] getIndex(String value){
        return new long[]{};
    }


    public RedisBloomFilter() {
        Preconditions.checkArgument(funnel != null, "funnel不能为空");
        this.funnel = (from, into) -> into.putString(from, Charsets.UTF_8)
                .putString(from, Charsets.UTF_8);
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }
        return offset;
    }

    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
}
