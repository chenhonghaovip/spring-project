package com.honghao.cloud.userapi.test.proxy;

/**
 * @author chenhonghao
 * @date 2020-03-09 12:43
 */
public class Client {
    public static void main(String[] args) {
        //Cglib代理
        CGLibProxy cgLibProxy = new CGLibProxy();
        Subject cgLib = (Subject) cgLibProxy.createProxyObject(new RealSubject());
        cgLib.doSomething();
        //jdk动态代理
        InvocationHandlerImpl invocationHandler = new InvocationHandlerImpl();
        Subject proxy = (Subject) invocationHandler.newProxy(new RealSubject());
        proxy.doSomething();
    }
}
