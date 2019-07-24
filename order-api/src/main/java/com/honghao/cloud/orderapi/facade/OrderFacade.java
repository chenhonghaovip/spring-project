package com.honghao.cloud.orderapi.facade;

/**
 * 订单信息操作
 *
 * @author chenhonghao
 * @date 2019-07-18 17:27
 */
public interface OrderFacade {
    /**
     * 创建用户
     * @param data data
     * @return Boolean
     */
    Boolean createUser(String data);
}
