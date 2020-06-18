package com.honghao.cloud.userapi.test.face.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2020-05-21 20:02
 */
public class Test11 {
    static AtomicInteger atomicInteger = new AtomicInteger(1);
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> test01(),"1").start();

        new Thread(() -> test01(),"2").start();

        new Thread(() -> test01(),"0").start();

    }

    private static void test01(){
        while (atomicInteger.get()<=99){
            reentrantLock.lock();
            try {
                if (atomicInteger.get()<=99 && atomicInteger.get()%3==Integer.valueOf(Thread.currentThread().getName())){
                    System.out.println(Thread.currentThread().getName()+"===="+atomicInteger.getAndIncrement());
                }
            } finally {
                reentrantLock.unlock();
            }
        }

    }
}
