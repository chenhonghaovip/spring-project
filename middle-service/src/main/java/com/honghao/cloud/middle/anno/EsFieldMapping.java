package com.honghao.cloud.middle.anno;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsFieldMapping {

    String alias() default "";
}
