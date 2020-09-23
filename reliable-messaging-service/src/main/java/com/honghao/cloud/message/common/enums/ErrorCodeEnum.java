package com.honghao.cloud.message.common.enums;

import com.honghao.cloud.basic.common.base.base.BaseErrorInfoInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误信息enum
 *
 * @author luxinghui
 * @date 2019/05/08
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements BaseErrorInfoInterface {
    /**
     * 成功
     */
    SUCCESS("200", "成功!"),

    /**
     * 请求的数据格式不符
     */
    BODY_NOT_MATCH("400","请求的数据格式不符!"),

    /**
     * 请求的数字签名不匹配
     */
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),

    /**
     * 未找到该资源
     */
    NOT_FOUND("404", "未找到该资源!"),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    /**
     * 成功
     */
    SERVER_BUSY("503","服务器正忙，请稍后再试!"),

    /**
     * 系统繁忙，请稍后重试
     */
    SYSTEM_ERROR("100000", "系统繁忙，请稍后重试"),

    /**
     * 参数错误
     */
    PARAM_ERROR("100001", "参数错误"),

    /**
     * 熔断异常
     */
    FAIL_BACK_ERROR("100002", "接口熔断异常"),

    /**
     * 系统异常 500 服务器的内部错误
     */
    EXCEPTION("100003", "服务器开小差，请稍后再试"),

    /**
     * 系统限流
     */
    TRAFFIC_LIMITING("100004", "哎呀，网络拥挤请稍后再试试"),

    /**
     * 服务调用异常
     */
    API_GATEWAY_ERROR("100005", "网络繁忙，请稍后再试"),


    /**
     * 数据不存在
     */
    DATA_DOES_NOT_EXIST("100006", "数据不存在"),

    /**
     * 数据无变化
     */
    NO_CHANGE_IN_DATA("100007", "数据无变化"),
    ;
    private String code;
    private String message;

    @Override
    public String getResultCode() {
        return code;
    }

    @Override
    public String getResultMsg() {
        return message;
    }
}
