package com.honghao.cloud.userapi.tactics;

import com.honghao.cloud.userapi.base.BaseResponse;
import org.springframework.stereotype.Service;

/**
 * 骑士app容器类
 *
 * @author chenhonghao
 * @date 2020-03-11 14:13
 */
@Service
public class DeliveryContext {
    private DeliveryService deliveryService;

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    /**
     * 抢单操作
     * @param data data
     * @return Result
     */
    public BaseResponse grabSingle(String data) {
        return deliveryService.grabSingle(data);
    }

    /**
     * 到店操作
     * @param data data
     * @return Result
     */
    public BaseResponse receiveShop(String data) {
        return deliveryService.receiveShop(data);
    }

    /**
     * 取单操作
     * @param data data
     * @return Result
     */
    public BaseResponse takeOrder(String data) {
        return deliveryService.takeOrder(data);
    }

    /**
     * 送达
     * @param data data
     * @return Result
     */
    public BaseResponse serviceOrder(String data){
        return deliveryService.serviceOrder(data);
    }

    /**
     * 骑士取消订单
     * @param data data
     * @return Result
     */
    public BaseResponse deliveryManCancel(String data) {
        return deliveryService.deliveryManCancel(data);
    }

    /**
     * 骑士异常订单
     * @param data data
     * @return Result
     */
    public BaseResponse deliveryManException(String data) {
        return deliveryService.deliveryManException(data);
    }
}
