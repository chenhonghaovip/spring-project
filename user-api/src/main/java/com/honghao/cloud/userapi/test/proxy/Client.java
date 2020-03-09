package com.honghao.cloud.userapi.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author chenhonghao
 * @date 2020-03-09 12:43
 */
public class Client {
    public static void main(String[] args) {
        Subject subject = new RealSubject();
        InvocationHandler invocationHandler = new InvocationHandlerImpl(subject);
        ClassLoader classLoader = subject.getClass().getClassLoader();
        Class[] interfaces = subject.getClass().getInterfaces();

        Subject proxy = (Subject) Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);
        proxy.doSomething();
        System.out.println("动态代理对象的类型："+subject.getClass().getName());
    }
}
