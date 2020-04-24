package com.honghao.cloud.userapi.spring.beanfactorypostprocessor;

import com.honghao.cloud.userapi.spring.bean.AppleC;
import com.honghao.cloud.userapi.spring.bean.AppleE;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @author chenhonghao
 * @date 2020-01-14 10:00
 */

public class TestBeanFactoryPostProcess implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition("appleA");
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(Objects.requireNonNull(beanDefinition.getBeanClassName()));
        beanDefinition.setBeanClassName(AppleC.class.getName());
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(AppleE.class);
        beanDefinitionRegistry.registerBeanDefinition(AppleE.class.getName(),rootBeanDefinition);
    }
}
