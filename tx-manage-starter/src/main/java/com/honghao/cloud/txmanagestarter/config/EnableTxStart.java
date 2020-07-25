package com.honghao.cloud.txmanagestarter.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author CHH
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(TxManagerAutoConfiguration.class)
public @interface EnableTxStart {
}
