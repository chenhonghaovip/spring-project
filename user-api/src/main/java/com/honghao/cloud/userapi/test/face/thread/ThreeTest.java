package com.honghao.cloud.userapi.test.face.thread;

import com.honghao.cloud.basic.common.base.factory.ExecutorFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2020-05-21 22:13
 */
public class ThreeTest {
    private static Lock lock = new ReentrantLock();
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) {
        Condition f1 = lock.newCondition();
        Condition f2 = lock.newCondition();
        Condition f3 = lock.newCondition();

        ThreadPoolExecutor threadPoolExecutor = ExecutorFactory.buildThreadPoolExecutor(3, 3, "test");
        threadPoolExecutor.submit(()->{
            while(atomicInteger.get()<=99) {
                lock.lock();
                try {
                    while(atomicInteger.get() %3 != 0) {
                        // 释放lock锁，当前线程进入等待状态,进入到f1的等待队列中去
                        f1.await();
                    }
                    if (atomicInteger.get()<=99){
                        System.out.println(Thread.currentThread().getName()+"========:"+atomicInteger.getAndIncrement());
                        // 唤醒线程2
                        f2.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        threadPoolExecutor.submit(()->{
            while(atomicInteger.get()<=99) {
                lock.lock();
                try {
                    while(atomicInteger.get() %3 != 1) {
                        f2.await();
                    }
                    if (atomicInteger.get()<=99){
                        System.out.println(Thread.currentThread().getName()+"========:"+atomicInteger.getAndIncrement());
                        //唤醒线程3
                        f3.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        threadPoolExecutor.submit(()->{
            while(atomicInteger.get()<=99) {
                lock.lock();
                try {
                    while(atomicInteger.get() %3 != 2) {
                        f3.await();
                    }
                    if (atomicInteger.get()<=99){
                        System.out.println(Thread.currentThread().getName()+"========:"+atomicInteger.getAndIncrement());
                        //唤醒线程1
                        f1.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
    }
}