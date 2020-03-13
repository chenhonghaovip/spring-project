package com.honghao.cloud.orderapi.test.order_service;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * 订单环境类
 *
 * @author chenhonghao
 * @date 2020-03-13 09:55
 */
@Getter
@Component
public class Context {

    public static final OrdersStatus ORDER_STATUS = new OrdersStatus();
    public static final ToShopStatus TO_SHOP_STATUS = new ToShopStatus();

    private BaseOrderStatus baseOrderStatus;


    public void setBaseOrderStatus(BaseOrderStatus baseOrderStatus) {
        this.baseOrderStatus = baseOrderStatus;
        this.baseOrderStatus.setContext(this);
    }

    public void orders() {
        baseOrderStatus.orders();
    }

    public void toShop() {
        baseOrderStatus.toShop();
    }

    public void pickup() {
        baseOrderStatus.pickup();
    }

    public void complete() {
        baseOrderStatus.complete();
    }

    public void cancel() {
        baseOrderStatus.cancel();
    }

    public void abnormal() {
        baseOrderStatus.abnormal();
    }
}
