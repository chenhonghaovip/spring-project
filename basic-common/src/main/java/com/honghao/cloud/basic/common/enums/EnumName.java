package com.honghao.cloud.basic.common.enums;

import java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumName {

    String value() default "";

    String fieldName() default "VALUES";

    Class<? extends  CommonInterface<?>> classes();

}
