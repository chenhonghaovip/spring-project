package com.honghao.cloud.orderapi.aspect;

import java.lang.annotation.*;

/**
 * 超时重试
 *
 * @author CHH
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeOut {
    /**
     * 默认超时时间
     *
     * @return int
     */
    int time() default 2;

    /**
     * 存储的键值
     *
     * @return String
     */
    String key();

    /**
     * 存储的队列
     *
     * @return String
     */
    String queue();
}
