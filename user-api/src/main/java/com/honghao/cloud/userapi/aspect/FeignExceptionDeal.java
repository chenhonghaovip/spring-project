package com.honghao.cloud.userapi.aspect;

import java.lang.annotation.*;

/**
 * feign接口超时处理
 *
 * @author chenhonghao
 * @date 2020-05-27 15:00
 */
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FeignExceptionDeal {
    /**
     * 重试次数
     * @return int 默认重试三次
     */
    int retryTimes() default 3;
}
