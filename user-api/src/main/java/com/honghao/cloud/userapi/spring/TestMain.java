package com.honghao.cloud.userapi.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:47
 */
public class TestMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext an = new AnnotationConfigApplicationContext(TestConfig.class);
        AppleA appleA = (AppleA) an.getBean("appleA");
        System.out.println(appleA);
    }
}
