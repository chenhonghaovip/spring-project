package com.honghao.cloud.userapi.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:47
 */
public class TestMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext an = new AnnotationConfigApplicationContext(TestConfig.class);


        BeanDefinition beanDefinition = new RootBeanDefinition(Fox.class);
        //将fox交给spring容器进行管理   注册到beanDefinitionMap中，key = beanName  value = beanDefinition;
//        an.registerBeanDefinition("fox",beanDefinition);
//        an.getBean("fox");

//        an.register(Fox.class);

        //注册对象到容器中
        Fox fox = new Fox();
        DefaultListableBeanFactory defaultListableBeanFactory = an.getDefaultListableBeanFactory();
        defaultListableBeanFactory.registerSingleton("fox",fox);

        an.getBean("fox");
    }
}
