package com.honghao.cloud.accountapi;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2020-08-05 20:26
 */
public class Test {
    private static Random random = new Random();
    private static ReentrantLock reentrantLock = new ReentrantLock();
    static AtomicInteger atomicInteger = new AtomicInteger(10);
    static AtomicInteger atomicInteger1 = new AtomicInteger(100);
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            new Thread(()->{
                reentrantLock.lock();
                try {
                    int sum =  atomicInteger1.get();
                    int a = random.nextInt(30);
                    if (atomicInteger.get()==1){
                        System.out.println(sum);
                        return;
                    }

                    while (sum-a<atomicInteger.get()-1 || a<1){
                        a = random.nextInt(30);
                    }
                    atomicInteger1.getAndSet(sum-a);
                    System.out.println(a);
                } finally {
                    atomicInteger.getAndDecrement();
                    reentrantLock.unlock();
                }
            }).run();
        }
    }
}
