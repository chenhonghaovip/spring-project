package com.honghao.cloud.userapi.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:47
 */
@Configuration
//导入类
@Import(value = {ImportSelectTest.class,AppleA.class,AppleBTest.class,AppleC.class})
@ComponentScan(basePackages = "com.honghao.cloud.userapi.spring")
//导入了一个MapperScannerConfigurer 的bean定义
//@MapperScan(value = "com.honghao.cloud.userapi.domain.mapper")
//@EnableEcho(packages = "com.honghao.cloud.userapi.spring")
public class TestConfig {

    @Bean
    @ConditionalOnClass(AppleB.class)
    public AppleE appleE(){
        return new AppleE();
    }
}
