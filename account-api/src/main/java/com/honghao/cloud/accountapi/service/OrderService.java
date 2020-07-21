package com.honghao.cloud.accountapi.service;

import com.honghao.cloud.accountapi.base.BaseResponse;

/**
 * 订单服务接口
 *
 * @author chenhonghao
 * @date 2019-07-18 17:31
 */
public interface OrderService {
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
}
