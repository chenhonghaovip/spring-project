package com.honghao.cloud.userapi.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author chenhonghao
 * @date 2020-03-09 12:52
 */
public class DynamicProxy<T> {
    public static <T> T newProxyInstance(ClassLoader classLoader, Class<?>[] interfaces, InvocationHandler invocationHandler){
        if (true){
//            BeforeAdvice beforeAdvice = new bef
//            (new BeforeAdvice()).ex
        }
        return (T) Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);
    }
}
