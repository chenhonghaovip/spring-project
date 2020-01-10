package com.honghao.cloud.userapi.test.springtest;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

/**
 * @author chenhonghao
 * @date 2020-01-08 10:10
 */
public class UserBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition("user");
        MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
        if (mutablePropertyValues.contains("name")){
            mutablePropertyValues.addPropertyValue("name","陈");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
