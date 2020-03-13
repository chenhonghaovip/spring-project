package com.honghao.cloud.userapi.spring.importin;

import com.honghao.cloud.userapi.spring.bean.AppleB;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chenhonghao
 * @date 2020-01-13 21:07
 */
public class AppleBTest implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(AppleB.class);
        beanDefinitionRegistry.registerBeanDefinition(AppleB.class.getName(),rootBeanDefinition);
    }
}
