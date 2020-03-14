package com.honghao.cloud.userapi.spring.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 注册条件筛选
 *
 * @author chenhonghao
 * @date 2020-03-13 16:26
 */

public class ComponentAExistsCondition implements Condition {

    /**
     *
     * @param conditionContext 判断条件能使用的上下文环境
     * @param annotatedTypeMetadata 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //1、获取ioc的beanFctory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //获取bean定义的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        return conditionContext.getBeanFactory().containsBean("com.honghao.cloud.userapi.spring.bean.AppleB");
    }
}
