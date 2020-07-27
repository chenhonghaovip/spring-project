package com.honghao.cloud.basic.common.base.base;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 基础断言处理
 *
 * @author chenhonghao
 * @date 2020-06-12 11:48
 */
public abstract class BaseAssert {

    /**
     * 断言当前对象非空，若为空，抛出异常
     * @param object object
     */
    public static void notNull(@Nullable Object object,String message){
        if (object == null){
            throw new BizException(message);
        }
    }

    /**
     * 断言当前对象非空，若为空，抛出异常
     * @param object object
     * @param baseErrorInfoInterface 错误信息
     */
    public static void notNull(@Nullable Object object,BaseErrorInfoInterface baseErrorInfoInterface){
        if (object == null){
            throw new BizException(baseErrorInfoInterface);
        }
    }

    /**
     * 断言当前对象为空，若不为空，抛出异常
     * @param object object
     */
    public static void isNull(@Nullable Object object,String message) {
        if (object != null) {
            throw new BizException(message);
        }
    }

    /**
     * 断言当前对象为空，若不为空，抛出异常
     * @param object object
     * @param baseErrorInfoInterface 错误信息
     */
    public static void isNull(@Nullable Object object, BaseErrorInfoInterface baseErrorInfoInterface) {
        if (object != null) {
            throw new BizException(baseErrorInfoInterface);
        }
    }

    /**
     * 断言当前对象为true，若不为true，抛出异常
     * @param result result
     * @param baseErrorInfoInterface 错误信息
     */
    public static void isTrue(BaseResponse result, BaseErrorInfoInterface baseErrorInfoInterface) {
        if (!result.isResult()) {
            throw new BizException(baseErrorInfoInterface);
        }
    }

    /**
     * 断言当前对象为true，若不为true，抛出异常
     * @param list list
     * @param baseErrorInfoInterface 错误信息
     */
    public static void isNotEmpty(List list, BaseErrorInfoInterface baseErrorInfoInterface) {
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException(baseErrorInfoInterface);
        }
    }
}
