package com.honghao.cloud.accountapi.service;


import com.honghao.cloud.basic.common.base.base.BaseResponse;

/**
 * 订单服务接口
 *
 * @author chenhonghao
 * @date 2019-07-18 17:31
 */
public interface AccountService {
    /**
     * 插入用户数据
     */
    void createOrders(String data);

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
    BaseResponse redisLcok(String userId);

    /**
     * redisson加锁操作
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redissonLcok(String userId);

    /**
     * redis实现list
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisList(String userId);
}
