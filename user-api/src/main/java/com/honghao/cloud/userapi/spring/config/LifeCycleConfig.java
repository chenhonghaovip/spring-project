package com.honghao.cloud.userapi.spring.config;

import com.honghao.cloud.userapi.spring.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * bean生命周期配置类
 * 1、通过在 @Bean 注解中添加initMethod和destroyMethod
 * 2、通过注解 @PostConstruct 和@PreDestroy
 * 3、通过实现BeanPostProcessor接口进行
 *
 * @author chenhonghao
 * @date 2020-03-14 22:37
 */
@PropertySource(value = "classpath:/application.properties")
@Configuration
@ComponentScan(basePackages = "com.honghao.cloud.userapi.spring")
public class LifeCycleConfig {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}
