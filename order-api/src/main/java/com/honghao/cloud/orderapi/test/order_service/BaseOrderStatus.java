package com.honghao.cloud.orderapi.test.order_service;

import lombok.Data;

/**
 * 订单状态抽象类
 *
 * @author chenhonghao
 * @date 2020-03-13 09:54
 */
@Data
public abstract class BaseOrderStatus {
    Context context;

    /**
     * 接单
     */
    public abstract void orders();

    /**
     * 到店
     */
    public abstract void toShop();

    /**
     * 取件
     */
    public abstract void pickup();

    /**
     * 完成
     */
    public abstract void complete();

    /**
     * 取消
     */
    public abstract void cancel();

    /**
     * 异常
     */
    public abstract void abnormal();
}
