package com.honghao.cloud.userapi.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author chenhonghao
 * @date 2020-01-20 14:31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RegisterTest.class})
public @interface EnableEcho {
    //传入包名
    String[] packages() default "";

}
