package com.honghao.cloud.userapi.spring;

import com.honghao.cloud.userapi.factory.ExecutorFactory;
import com.honghao.cloud.userapi.spring.bean.AppleD;
import com.honghao.cloud.userapi.spring.bean.AsyncTest;
import com.honghao.cloud.userapi.spring.bean.Car;
import com.honghao.cloud.userapi.spring.bean.Fox;
import com.honghao.cloud.userapi.spring.config.AOPConfig;
import com.honghao.cloud.userapi.spring.config.AsyncConfig;
import com.honghao.cloud.userapi.spring.config.LifeCycleConfig;
import com.honghao.cloud.userapi.spring.config.TestConfig;
import com.honghao.cloud.userapi.spring.factorybean.MyFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:47
 */
@Slf4j
public class TestMain {

    private static final ThreadPoolExecutor EXECUTOR = ExecutorFactory.buildThreadPoolExecutor(2,4,"test_");
    public static void main(String[] args) {
        AnnotationConfigApplicationContext an = new AnnotationConfigApplicationContext(TestConfig.class);
        AppleD appleD = (AppleD) an.getBean(AppleD.class.getName());

        /*
         * 可以通过factoryBean对其实现代理，获取的factoryBean获取到的是其中getObject方法的返回类型的bean
         * 只有在bean前添加&,才可以获取自身的bean
         */
//        Object object = an.getBean("&myFactoryBean");
//        System.out.println("test - myFactoryBean:"+object);

        //将fox交给spring容器进行管理   注册到beanDefinitionMap中，key = beanName  value = beanDefinition;
        BeanDefinition beanDefinition = new RootBeanDefinition(Fox.class);
        an.registerBeanDefinition("fox",beanDefinition);
        System.out.println(an.getBean("fox"));

        MyFactoryBean myFactoryBean = new MyFactoryBean();
        T t = myFactoryBean.getObject();
        System.out.println(t);

        String[] beanDefinitionNames = an.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(each-> System.out.println(each));
//        an.register(Fox.class);

        //注册对象到容器中
//        Fox fox = new Fox();
//        DefaultListableBeanFactory defaultListableBeanFactory = an.getDefaultListableBeanFactory();
//        defaultListableBeanFactory.registerSingleton("fox",fox);
//        an.getBean("fox");
    }
    @Test
    public void test(){
        AnnotationConfigApplicationContext an = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        Car car = (Car) an.getBean("car");
        System.out.println(car);
        each(an);
        //容器关闭
        an.close();
    }

    public static void each(AnnotationConfigApplicationContext an){
        String[] beanDefinitionNames = an.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(System.out::println);
    }

    @Test
    public void test11(){
        AnnotationConfigApplicationContext an = new AnnotationConfigApplicationContext(AOPConfig.class);
        Fox fox = (Fox) an.getBean("fox");
        fox.sout();
    }

    @Test
    public void test001(){
        System.out.println("1111");
        EXECUTOR.submit(()-> log.info("222222"));
        CompletableFuture.runAsync(()-> System.out.println("333333"),EXECUTOR);
        int aging = 20;
        int hz = 7;
        int i = Math.addExact(aging, hz) % hz;
        System.out.println(i);
    }

    @Test
    public void test002(){
        AnnotationConfigApplicationContext an = new AnnotationConfigApplicationContext(AsyncConfig.class);
        AsyncTest asyncTest = (AsyncTest) an.getBean("asyncTest");
        asyncTest.test();

        Fox bean = (Fox) an.getBean("fox");

    }
}
