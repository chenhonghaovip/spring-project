package com.honghao.cloud.message.common.enums;

import lombok.Getter;

/**
 * 订单类型枚举
 *
 * @author chenhonghao
 * @date 2019-09-18 14:03
 */
@Getter
public enum MsgStatusEnum {

    /**
     * 待确认
     */
    TO_BE_CONFIRMED(0, "待确认"),

    /**
     * 已发送
     */
    HAS_BEEN_SENT(1, "已发送"),

    /**
     * 已完成
     */
    COMPLETED(2, "已完成"),
    ;

    private Integer code;
    private String desc;

    MsgStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
