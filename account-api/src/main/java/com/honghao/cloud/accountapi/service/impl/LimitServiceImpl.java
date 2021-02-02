package com.honghao.cloud.accountapi.service.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.honghao.cloud.accountapi.config.RedisConfig;
import com.honghao.cloud.accountapi.dto.common.SlidingWindow;
import com.honghao.cloud.accountapi.dto.common.TokenBucket;
import com.honghao.cloud.accountapi.service.LimitService;
import com.honghao.cloud.basic.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenhonghao
 * @date 2021-01-21 15:26
 */
@Slf4j
@Service
public class LimitServiceImpl implements LimitService {
    private static final ConcurrentHashMap<String, SlidingWindow> MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, TokenBucket> TOKEN_BUCKET_MAP = new ConcurrentHashMap<>();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 单机滑动窗口算法
     * @return BaseResponse
     */
    @Override
    public BaseResponse singleMachineSlidingWindow() {
        String key = "singleMachineSlidingWindow";
        SlidingWindow slidingWindow = new SlidingWindow(2, 10);
        if (Objects.nonNull(MAP.putIfAbsent(key, slidingWindow))) {
            slidingWindow = MAP.get(key);
        }
        long l = System.currentTimeMillis();
        if (!slidingWindow.addCount(l)) {
            System.out.println("达到阈值" + l);
            return BaseResponse.error();
        }
        System.out.println("执行业务逻辑" + l);
        return BaseResponse.success();
    }

    /**
     * 单机令牌桶 -- 通过延迟写的方式存入令牌，好处是存入令牌时不需要创建不同的定时任务来操作，简化流程
     * @return BaseResponse
     */
    @Override
    public BaseResponse singleTokenBucket() {
        String key = "singleTokenBucket";
        long l = System.currentTimeMillis();
        // 延迟计算方式
        boolean resync = resync(key, 5, l);

        if (!resync) {
            System.out.println("达到阈值" + l);
            return BaseResponse.error();
        }
        System.out.println("执行业务逻辑" + l);
        return BaseResponse.success();
    }

    /**
     * 单机漏桶
     * @return BaseResponse
     */
    @Override
    public BaseResponse singleLeakyBucket() {
        RateLimiter rateLimiter = RateLimiter.create(0.5);
        String key = "singleLeakyBucket";
        return null;
    }

    /**
     * 分布式环境下的滑动窗口限流算法
     * @param param param
     * @return BaseResponse
     */
    @Override
    public BaseResponse redisSlidingWindow(String param) {
        return null;
    }

    /**
     * 分布式环境下的令牌桶限流算法
     * @param param param
     * @return BaseResponse
     */
    @Override
    public BaseResponse redisTokenBucket(String param) {
        String key = "tokenBucket-resync";
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.eval(RedisConfig.TOKEN_BUCKET_CURRENT_LIMIT.getBytes(), ReturnType.VALUE, 1, key.getBytes(), String.valueOf(10).getBytes(), String.valueOf(9).getBytes());
                return null;
            }
        });

        redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                RedisZSetCommands redisZSetCommands = redisConnection.zSetCommands();
                redisZSetCommands.zCard(key.getBytes());
                return null;
            }
        });



        Object o = redisTemplate.opsForList().leftPop(key);
        if (Objects.isNull(o)) {
            return BaseResponse.error("达到请求阈值阀门,请稍后重试");
        }
        System.out.println("消费的当前token值为：" + o);
        return BaseResponse.success();
    }

    /**
     * 取出令牌，并且在消费最后一个令牌时生成之前本该生成的令牌
     * 问题：当令牌桶中令牌很长时间不消费时，造成令牌桶中最后一个令牌的时间距离现在很长，突然大流量访问，导致令牌桶
     * 瞬间清空，再次延迟计算令牌时，本来只应该放入单位时间令牌，
     *
     * @param key 该令牌桶对应的接口或业务
     * @param rate 令牌入桶频率(n个/s)
     */
    private synchronized boolean resync(String key, int rate, long currentTimeMillis) {

        TokenBucket tokenBucket = TokenBucket.builder().capacity(10).currentSize(10).rate(rate).lastRefillTime(currentTimeMillis).build();
        if (Objects.nonNull(TOKEN_BUCKET_MAP.putIfAbsent(key, tokenBucket))) {
            tokenBucket = TOKEN_BUCKET_MAP.get(key);
        }

        // 获取当前key的最后一个令牌进入令牌桶的时间
        long aLong = tokenBucket.getLastRefillTime();

        // 如果最终时间不为空，向令牌桶中插入 (currentTimeMillis - aLong) / timeMillisPerSlice个令牌，因为令牌桶大小限制，所以取Math.min(max, a + l)
        int insert = (int) (((currentTimeMillis - aLong) / 1000) * rate);
        // 如果此时可以继续添加令牌
        int value = tokenBucket.getCurrentSize();
        if (insert > 0) {
            tokenBucket.setLastRefillTime(currentTimeMillis);
            value = Math.min(tokenBucket.getCapacity(), value + insert);
        }

        if (value > 0) {
            tokenBucket.setCurrentSize(value - 1);
            return true;
        }
        return false;
    }
}
