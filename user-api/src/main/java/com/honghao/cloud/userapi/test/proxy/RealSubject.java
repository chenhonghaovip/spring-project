package com.honghao.cloud.userapi.test.proxy;

/**
 * @author chenhonghao
 * @date 2020-03-09 12:51
 */
public class RealSubject implements Subject{
    @Override
    public void doSomething() {
        System.out.println("do something.........");
    }
}
