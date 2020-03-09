package com.honghao.cloud.userapi.test.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chenhonghao
 * @date 2020-03-09 12:44
 */
public class InvocationHandlerImpl implements InvocationHandler {
    private Object object;

    public InvocationHandlerImpl(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("在调用之前，我要干点啥呢？");
        System.out.println("Method:" + method);
        Object result = method.invoke(this.object,args);
        System.out.println("在调用之后，我要干点啥呢？");
        return result;
    }
}
