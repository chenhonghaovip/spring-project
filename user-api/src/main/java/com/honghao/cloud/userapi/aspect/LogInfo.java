package com.honghao.cloud.userapi.aspect;

import java.lang.annotation.*;

/**
 * @author CHH
 */
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInfo {
}
