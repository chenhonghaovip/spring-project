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

    BaseResponse redisTest(String userId);
}
