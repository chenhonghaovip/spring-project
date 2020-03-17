package com.honghao.cloud.userapi.spring.beanfactorypostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author chenhonghao
 * @date 2020-03-14 23:32
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     * 在bean初始化之前调用
     *
     * @param bean bean
     * @param beanName beanName
     * @return bean
     * @throws BeansException ea
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization" + bean);
        return bean;
    }

    /**
     * 在bean初始化之后调用
     *
     * @param bean bean
     * @param beanName beanName
     * @return bean
     * @throws BeansException ea
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization" + bean);
        return bean;
    }
}
