package com.honghao.cloud.orderapi.aspect;

import java.lang.annotation.*;

/**
 * @author CHH
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TryAgain {
    int value() default 3;
}
