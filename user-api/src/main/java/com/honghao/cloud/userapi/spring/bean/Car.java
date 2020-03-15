package com.honghao.cloud.userapi.spring.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author chenhonghao
 * @date 2020-03-14 22:41
 */
@Data
@NoArgsConstructor
public class Car implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Value("张三")
    private String name;

    @Value("${car.color}")
    private String color;

    public void init(){
        System.out.println("car init...");
    }

    public void destroy(){
        System.out.println("car destroy...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
