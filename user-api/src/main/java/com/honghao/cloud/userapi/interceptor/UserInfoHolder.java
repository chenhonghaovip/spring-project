package com.honghao.cloud.userapi.interceptor;

import com.honghao.cloud.userapi.dto.request.Operator;

/**
 * 操作人信息容器
 *
 * @author luxinghui
 * @date 2019-05-14
 */
public class UserInfoHolder {
    /**
     * 请求线程获取用户信息
     * 切换多线程前请先获取
     */
    private static final ThreadLocal<Operator> OPERATOR_CONTAINER = new InheritableThreadLocal<>();

    /**
     * 移除用户信息
     */
    static void removeOperator() {
        OPERATOR_CONTAINER.remove();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static Operator getOperator() {
        return OPERATOR_CONTAINER.get();
    }

    /**
     * 设置用户信息
     *
     * @param operator
     */
    static void setOperator(Operator operator) {
        OPERATOR_CONTAINER.set(operator);
    }

}
