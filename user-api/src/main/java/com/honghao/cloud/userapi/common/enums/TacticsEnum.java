package com.honghao.cloud.userapi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 策略枚举
 *
 * @author chenhonghao
 * @date 2020-03-11 14:40
 */
@Getter
@AllArgsConstructor
public enum TacticsEnum {
    /**
     * 同城订单处理
     */
    SAME_CITY_SERVICE(2, "sameCityService"),
    /**
     * 快递订单处理
     */
    DELIVERY_ORDER_SERVICE(4, "deliveryOrderService");
    public final static List<Integer> CODES = Arrays.stream(TacticsEnum.values()).map(TacticsEnum::getOrderType).collect(Collectors.toList());
    private Integer orderType;
    private String deliveryServiceName;

    public static TacticsEnum formCode(Integer code) {
        if (CODES.contains(code)) {
            for (TacticsEnum value : TacticsEnum.values()) {
                if (value.getOrderType().equals(code)) {
                    return value;
                }
            }
        }
        throw new RuntimeException("订单状态参数异常，请检查！");
    }

}
