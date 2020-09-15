package com.honghao.cloud.accountapi;

/**
 * @author chenhonghao
 * @date 2020-08-12 19:58
 */
public class Test02 {
    public void syncTest(){
        synchronized (Test02.class){
            System.out.println("syncTest");
        }
    }

    public void syncBlock(){
        synchronized (this){
            System.out.println("hello block");
        }
    }
    public synchronized void syncMethod(){
        System.out.println("hello method");
    }

    public static synchronized void test(){
        System.out.println("static synchronized");
    }
}
