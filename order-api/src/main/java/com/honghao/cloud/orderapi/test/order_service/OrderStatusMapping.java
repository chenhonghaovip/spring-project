package com.honghao.cloud.orderapi.test.order_service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单状态映射
 *
 * @author chenhonghao
 * @date 2020-03-13 11:01
 */

@Getter
@AllArgsConstructor
public enum OrderStatusMapping {
    /**
     * 接单
     */
    ORDERS(1, "ordersStatus"),
    /**
     * 到店
     */
    TO_SHOP(3, "toShopStatus"),
    ;
    private Integer orderStatus;
    private String desc;

    public static final List<Integer> CODES = Arrays.stream(OrderStatusMapping.values()).map(OrderStatusMapping::getOrderStatus).collect(Collectors.toList());

    public static OrderStatusMapping formCode(Integer orderStatus){
        if (!CODES.contains(orderStatus)){
            throw new RuntimeException("订单状态异常");
        }
        return Arrays.stream(OrderStatusMapping.values()).filter(each -> each.getOrderStatus().equals(orderStatus)).findFirst().orElse(null);
    }
}
