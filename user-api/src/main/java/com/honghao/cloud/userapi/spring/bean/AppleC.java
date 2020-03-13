package com.honghao.cloud.userapi.spring.bean;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:57
 */
public class AppleC {
    private Class<T> mapperInterface;

    private Integer age;

    private String name;

    public AppleC(Class<T> mapperInterface) {
        System.out.println("AppleC的构造器 === "+ mapperInterface);
        this.mapperInterface = mapperInterface;
    }

    public AppleC(){
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
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
