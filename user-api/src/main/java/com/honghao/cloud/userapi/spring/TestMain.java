package com.honghao.cloud.userapi.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:47
 */
public class TestMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext an = new AnnotationConfigApplicationContext(TestConfig.class);
        AppleD appleD = (AppleD) an.getBean(AppleD.class.getName());

        /*
         * 可以通过factoryBean对其实现代理，获取的factoryBean获取到的是其中getObject方法的返回类型的bean
         * 只有在bean前添加&,才可以获取自身的bean
         */
        Object object = an.getBean("&myFactoryBean");
        System.out.println("test - myFactoryBean:"+object);

        //将fox交给spring容器进行管理   注册到beanDefinitionMap中，key = beanName  value = beanDefinition;
        BeanDefinition beanDefinition = new RootBeanDefinition(Fox.class);
        an.registerBeanDefinition("fox",beanDefinition);
        System.out.println(an.getBean("fox"));

//        an.register(Fox.class);

        //注册对象到容器中
//        Fox fox = new Fox();
//        DefaultListableBeanFactory defaultListableBeanFactory = an.getDefaultListableBeanFactory();
//        defaultListableBeanFactory.registerSingleton("fox",fox);
//
//        an.getBean("fox");
    }
}
