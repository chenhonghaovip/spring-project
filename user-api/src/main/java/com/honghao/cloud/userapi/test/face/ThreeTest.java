package com.honghao.cloud.userapi.test.face;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2020-05-21 22:13
 */
public class ThreeTest {
    private static int count = 0;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Condition f1 = lock.newCondition();
        Condition f2 = lock.newCondition();
        Condition f3 = lock.newCondition();

        new Thread(()->{
            while(true) {
                lock.lock();
                try {
                    while(count %3 != 0) {
                        //刚开始count为0  0%3=0 所以此线程执行  执行完之后 唤醒现成2，由于此时count已经进行了++，所有while成立，c1进入等待状态，其他两个也一样
                        f1.await();
                    }
                    System.out.println(Thread.currentThread().getName()+"========:A");
                    count++;
                    //唤醒线程2
                    f2.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }) .start();

        new Thread(()->{
            while(true) {
                lock.lock();
                try {
                    while(count %3 != 1) {
                        f2.await();
                    }
                    System.out.println(Thread.currentThread().getName()+"========:B");
                    count++;
                    //唤醒线程3
                    f3.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }) .start();

        new Thread(()->{
            while(true) {
                lock.lock();
                try {
                    while(count %3 != 2) {
                        f3.await();
                    }
                    System.out.println(Thread.currentThread().getName()+"========:C");
                    count++;
                    //唤醒线程1
                    f1.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }) .start();

    }
}