package com.honghao.cloud.userapi.test.face.thread;

import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过使用wait和notify实现线程协同
 *
 * @author chenhonghao
 * @date 2020-05-22 09:15
 */
public class ThreadCoordinationTest1 {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private final Object object = new Object();

    public static void main(String[] args) {
        ThreadCoordinationTest1 threadCoordinationTest = new ThreadCoordinationTest1();
        threadCoordinationTest.test();
    }

    public void test() {

        ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(3, 3, "test");
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        threadPoolExecutor.submit(() -> {
            int a;
            while ((a = atomicInteger.get()) <= 99) {
                if (a % 3 == 0) {
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "==" + atomicInteger.getAndIncrement());
                        object.notifyAll();
                    }
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            int a;
            while ((a = atomicInteger.get()) <= 99) {
                if (a % 3 == 1) {
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "==" + atomicInteger.getAndIncrement());
                        object.notifyAll();
                    }
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            int a;
            while ((a = atomicInteger.get()) <= 99) {
                if (a % 3 == 2) {
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "==" + atomicInteger.getAndIncrement());
                        object.notifyAll();
                    }
                }
            }
        });

    }
}
