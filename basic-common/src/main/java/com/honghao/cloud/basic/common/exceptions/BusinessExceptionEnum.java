package com.honghao.cloud.basic.common.exceptions;

import lombok.AllArgsConstructor;

/**
 * @author chenhonghao12
 * @version 1.0
 */
@AllArgsConstructor
public enum BusinessExceptionEnum implements CommonException {

    NO_PERMISSION(1, "无权限操作");

    /**
     * 错误码
     */
    private final Integer errorCode;
    /**
     * 错误信息
     */
    private final String errorMsg;


    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
