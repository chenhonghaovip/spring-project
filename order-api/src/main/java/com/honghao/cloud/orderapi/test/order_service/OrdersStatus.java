package com.honghao.cloud.orderapi.test.order_service;

import org.springframework.stereotype.Service;

/**
 * 下单状态
 *
 * @author chenhonghao
 * @date 2020-03-13 10:00
 */
@Service
public class OrdersStatus extends BaseOrderStatus {
    @Override
    public void orders() {
        System.out.println("error");
    }

    @Override
    public void toShop() {
        System.out.println("正常接单到店");
    }

    @Override
    public void pickup() {
        System.out.println("error");
    }

    @Override
    public void complete() {
        System.out.println("error");
    }

    @Override
    public void cancel() {
        System.out.println("取消订单");
    }

    @Override
    public void abnormal() {
        System.out.println("error");
    }
}
