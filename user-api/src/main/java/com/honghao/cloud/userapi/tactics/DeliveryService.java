package com.honghao.cloud.userapi.tactics;

import com.honghao.cloud.userapi.base.BaseResponse;

/**
 * 骑士操作接口
 *
 * @author chenhonghao
 * @date 2020-03-11 14:15
 */
public interface DeliveryService {
    /**
     * 抢单操作
     * @param data data
     * @return Result
     */
    BaseResponse grabSingle(String data) ;

    /**
     * 到店操作
     * @param data data
     * @return Result
     */
    BaseResponse receiveShop(String data) ;

    /**
     * 取单操作
     * @param data data
     * @return Result
     */
    BaseResponse takeOrder(String data) ;

    /**
     * 送达
     * @param data data
     * @return Result
     */
    BaseResponse serviceOrder(String data);

    /**
     * 骑士取消订单
     * @param data data
     * @return Result
     */
    BaseResponse deliveryManCancel(String data) ;

    /**
     * 骑士异常订单
     * @param data data
     * @return Result
     */
    BaseResponse deliveryManException(String data) ;
}
