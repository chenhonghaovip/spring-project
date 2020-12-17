package com.honghao.cloud.userapi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单状态枚举
 *
 * @author chenhonghao
 * @date 2019-12-26 15:08
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrderStatusEnum {
    /**
     * 待分配
     */
    PENDING_ALLOCATION(0, "待分配"),
    /**
     * 待取件
     */
    RECEIPT(1, "待取件"),
    /**
     * 待送达
     */
    TO_BE_SERVED(2, "待送达"),
    /**
     * 已完成
     */
    COMPLETED(3, "已完成"),
    /**
     * 取消单
     */
    CANCELLATION(4, "取消单"),
    /**
     * 异常单
     */
    EXCEPTION_TICKET(5, "异常单");

    public static final List<Integer> CODES = Arrays.stream(OrderStatusEnum.values()).map(OrderStatusEnum::getCode).collect(Collectors.toList());
    private Integer code;
    private String desc;

    public OrderStatusEnum formCode(@NotNull Integer code) {
        if (!CODES.contains(code)) {
            throw new RuntimeException("订单状态异常");
        }
        return Arrays.stream(OrderStatusEnum.values()).filter(each -> code.equals(each.getCode())).findFirst().orElse(null);
    }

}
