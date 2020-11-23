package com.honghao.cloud.basic.common.base;

/**
 * 错误信息提示
 *
 * @author chenhonghao
 * @date 2020-03-12 10:54
 */
public interface BaseErrorInfoInterface {
    /**
     * 错误码
     * @return 错误码
     */
    String getResultCode();

    /**
     * 错误描述
     * @return 错误描述
     */
    String getResultMsg();
}
