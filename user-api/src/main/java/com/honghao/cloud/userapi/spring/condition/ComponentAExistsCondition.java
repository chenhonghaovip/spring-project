package com.honghao.cloud.userapi.spring.condition;

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

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return conditionContext.getBeanFactory().containsBean("com.honghao.cloud.userapi.spring.bean.AppleB");
    }
}
