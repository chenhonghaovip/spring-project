package com.honghao.cloud.userapi.spring;

import org.springframework.stereotype.Component;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:57
 */
@Component
public class AppleB {
    private Integer age;

    private String name;

    public AppleB() {
        System.out.println("AppleB的构造器");
    }
}
