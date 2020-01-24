package com.honghao.cloud.userapi.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Spring 中有两种类型的Bean，一种是普通Bean，另一种是工厂Bean
 * 即 FactoryBean。FactoryBean跟普通Bean不同，其返回的对象不是指定类的一个实例，
 * 而是该FactoryBean的getObject方法所返回的对象。
 *
 * @author chenhonghao
 * @date 2020-01-20 19:52
 */
@Component
@Slf4j
public class MyFactoryBean implements FactoryBean<T> {
    @Override
    public T getObject() {
        return new T();
    }

    @Override
    public Class<T> getObjectType() {
        return T.class;
    }
}
