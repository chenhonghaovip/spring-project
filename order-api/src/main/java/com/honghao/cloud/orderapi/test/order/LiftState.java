package com.honghao.cloud.orderapi.test.order;

import lombok.Data;

/**
 * 定义电梯行为：打开、关闭、运行、停止
 *
 * @author chenhonghao
 * @date 2020-03-12 16:49
 */
@Data
public abstract class LiftState {
    Lift lift;
    /**
     * 行为：打开电梯门
     */
    public abstract void open();

    /**
     * 行为：关闭电梯门
     */
    public abstract void close();

    /**
     * 行为：电梯运行
     */
    public abstract void run();

    /**
     * 行为：电梯停止运行
     */
    public abstract void stop();
}
