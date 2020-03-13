package com.honghao.cloud.orderapi.test.order_service;

import org.springframework.stereotype.Service;

/**
 * 到店状态
 *
 * @author chenhonghao
 * @date 2020-03-13 10:01
 */
@Service
public class ToShopStatus extends BaseOrderStatus{
    @Override
    public void orders() {

    }

    @Override
    public void toShop() {
        System.out.println("已经进行过到店操作了");
    }

    @Override
    public void pickup() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void abnormal() {

    }
}
