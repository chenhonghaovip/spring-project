package com.honghao.cloud.userapi.test.face.thread;

import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition接口实现线程协同
 *
 * @author chenhonghao
 * @date 2020-05-22 14:16
 */
public class ConditionTest {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        Condition condition = reentrantLock.newCondition();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(3, 3, "test");
        threadPoolExecutor.submit(() -> {
            while (true) {
                reentrantLock.lock();
                try {
                    while (atomicInteger.get() % 3 != 0) {
                        condition.await();
                    }
                    if (atomicInteger.get() <= 99) {
                        System.out.println(Thread.currentThread().getName() + "===" + atomicInteger.getAndIncrement());
                        condition.signalAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            while (true) {
                reentrantLock.lock();
                try {
                    while (atomicInteger.get() % 3 != 1) {
                        condition.await();
                    }
                    if (atomicInteger.get() <= 99) {
                        System.out.println(Thread.currentThread().getName() + "===" + atomicInteger.getAndIncrement());
                        condition.signalAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            while (true) {
                reentrantLock.lock();
                try {
                    while (atomicInteger.get() % 3 != 2) {
                        condition.await();
                    }
                    if (atomicInteger.get() <= 99) {
                        System.out.println(Thread.currentThread().getName() + "===" + atomicInteger.getAndIncrement());
                        condition.signalAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        });
    }
}
