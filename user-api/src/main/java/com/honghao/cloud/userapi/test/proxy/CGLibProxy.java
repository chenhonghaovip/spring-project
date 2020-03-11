package com.honghao.cloud.userapi.test.proxy;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLibProxy代理
 *
 * @author chenhonghao
 * @date 2020-03-10 22:34
 */
public class CGLibProxy implements MethodInterceptor {
    private Object targetObject;
    Object createProxyObject(Object obj) {
        this.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        //这一步就是告诉cglib，生成的子类需要继承哪个类
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        //生成源代码,编译成class文件,加载到JVM中，并返回被代理对象
        return enhancer.create();
    }
    private void checkPopedom() {
        System.out.println("======检查权限checkPopedom()======");
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 过滤方法
        if ("doSomething".equals(method.getName())) {
            checkPopedom();
        }
        return method.invoke(targetObject, objects);
    }
}
