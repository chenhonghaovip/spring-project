package com.honghao.cloud.userapi.spring;

import org.springframework.stereotype.Component;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:57
 */
@Component
public class AppleA {
    private Integer age;

    private String name;

    public AppleA() {
        System.out.println("AppleA的构造器");
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
