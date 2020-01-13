package com.honghao.cloud.userapi.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:47
 */
@Configuration
//导入类
//@Import(value = {AppleC.class})
@Import(value = {ImportSelectTest.class})
@ComponentScan(basePackages = "com.honghao.cloud.userapi.spring")
public class TestConfig {
}
