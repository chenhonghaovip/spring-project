package com.honghao.cloud.accountapi.service;

import com.honghao.cloud.basic.common.base.base.BaseResponse;

/**
 * @author chenhonghao
 * @date 2020-07-23 21:31
 */
public interface RedisService {

    /**
     * redis缓存击穿处理
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse cacheBreakdown(String userId);

    /**
     * 缓存穿透
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse cachePenetration(String userId);

    /**
     * redis加锁操作
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisLock(String userId);

    /**
     * redisson加锁操作
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redissonLock(String userId);

    /**
     * redis实现list
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisList(String userId);

    /**
     * redis实现Set
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisSet(String userId);

    /**
     * redis实现ZSet
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisZSet(String userId);

    /**
     * redis模拟实现微博热搜排行榜（点击）
     * @return BaseResponse
     */
    BaseResponse hotSearchOnWeibo(String key);
}
