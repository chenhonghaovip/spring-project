package com.honghao.cloud.accountapi.facade;


import com.honghao.cloud.basic.common.base.base.BaseResponse;

/**
 * 订单信息操作
 *
 * @author chenhonghao
 * @date 2019-07-18 17:27
 */
public interface AccountFacade {

    /**
     * 创建订单
     * @param data data
     * @return Boolean
     */
    BaseResponse createOrders(String data);
}
