package com.honghao.cloud.userapi.aspect;

import java.lang.annotation.*;

/**
 * 自定义注解
 *
 * @author chenhonghao
 * @date 2019-07-18 17:34
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
}
