package com.honghao.cloud.accountapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis序列化配置
 *
 * @author chenhonghao
 * @date 2020-07-23 20:46
 */
@Configuration
public class RedisConfig {
    /**
     * 对比并且删除redis锁
     * KEYS[1] redis中的key值
     * ARGV[1] 该key中期望的value值
     */
    public static final String UNLOCK = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 滑动窗口限流
     * KEYS[1] redis中的key值
     * ARGV[1] 删除的score上限
     * ARGV[2] 单位时间内访问上限次数
     * ARGV[3] 新成员的score
     * ARGV[4] 新成员内容
     */
    public static final String SLIDING_WINDOW_CURRENT_LIMIT = "redis.call('ZREMRANGEBYSCORE',KEYS[1],0,ARGV[1]) if redis.call('zcard',KEYS[1])<tonumber(ARGV[2]) then redis.call('zadd',KEYS[1],ARGV[3],ARGV[4]) return 1 else return 0 end";

    /**
     * 令牌桶限流，只保持存有固定个数的令牌，满了之后不再往里面放
     * KEYS[1] redis中的key值
     * argv[1] = 每次放入的令牌个数
     * argv[2] = 该队列中最多持有的令牌个数 - 1
     */
    public static final String TOKEN_BUCKET_CURRENT_LIMIT = "for i=1,ARGV[1] do redis.call('RPUSH',KEYS[1],i) end redis.call('LTRIM',KEYS[1],tonumber(0),tonumber(ARGV[2]))";

    /**
     * 令牌桶限流，只保持存有固定个数的令牌，满了之后不再往里面放
     * KEYS[1] redis中的key值
     * argv[1] = 每次放入的令牌个数
     * argv[2] = 该队列中最多持有的令牌个数 - 1
     */
    public static final String TOKEN_BUCKET_CURRENT_LIMIT_RESYNC = "for i=1,ARGV[1] do redis.call('RPUSH',KEYS[1],i) end redis.call('LTRIM',KEYS[1],tonumber(0),tonumber(ARGV[2]))";


    /**
     * 存在问题，通过这种序列化方式存进去的value值，会带引号""
     * @param redisConnectionFactory redisConnectionFactory
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
