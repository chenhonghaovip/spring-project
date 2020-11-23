package com.honghao.cloud.orderapi.service;

import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.orderapi.domain.entity.Order;

import java.util.List;

/**
 * 订单服务接口
 *
 * @author chenhonghao
 * @date 2019-07-18 17:31
 */
public interface OrderService {
    /**
     * 插入用户数据
     * @param data data
     * @return BaseResponse
     */
    BaseResponse createOrders(Order data);

    /**
     * 插入用户数据
     * @param data data
     * @return BaseResponse
     */
    BaseResponse createBatchOrders(List<Order> data);

    /**
     * 插入用户数据
     * @param wIds data
     * @return BaseResponse
     */
    List<String> batchQuery(List<String> wIds);

    /**
     * 乐观锁更新重试测试
     * @param  wId wId
     * @return BaseResponse
     */
    BaseResponse update(String wId);
}
