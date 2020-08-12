package com.honghao.cloud.accountapi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2020-08-12 19:31
 */
public class Test1 {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static final int num = 10;
    private static List<String> list = new ArrayList<>();
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Condition f1 = reentrantLock.newCondition();
        Condition f2 = reentrantLock.newCondition();
        Condition f3 = reentrantLock.newCondition();
        new Thread(() -> {
            while (atomicInteger.get()<num){
                reentrantLock.lock();
                try {
                    if (list.isEmpty() || "i".equals(list.get(list.size()-1))){
                        list.add("A");
                        f2.signal();
//                        System.out.println("A");
                    }else {
                        f1.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }

        }).start();

        new Thread(() -> {
            while (atomicInteger.get()<num){
                reentrantLock.lock();
                try {
                    if ("A".equals(list.get(list.size()-1))){
                        list.add("1");
                        f3.signal();
//                        System.out.println("1");
                    }else {
                        f2.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }

        }).start();

        new Thread(() -> {
            while (atomicInteger.get()<num){
                reentrantLock.lock();
                try {
                    if ("1".equals(list.get(list.size()-1))){
                        list.add("i");
                        f1.signal();
//                        System.out.println("i");

                        int a = atomicInteger.incrementAndGet();
                        if (a==num){
                            countDownLatch.countDown();
                        }
                    }else {
                        f3.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }


        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.toArray());


    }
}
