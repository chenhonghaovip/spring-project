package com.honghao.cloud.userapi.test.springtest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * @author chenhonghao
 * @date 2020-01-08 10:10
 */
public class UserBeanPostProcessor implements BeanPostProcessor, Ordered {
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor第" + getOrder() + "次被调动\n");
        if (bean instanceof User) {
            ((User) bean).setName("陈宏浩");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof User) {
            ((User) bean).setAge(25);
        }
        return bean;
    }
}
