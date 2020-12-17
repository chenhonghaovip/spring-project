package com.honghao.cloud.userapi.test.face.thread;

import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过使用wait和notify实现线程协同
 *
 * @author chenhonghao
 * @date 2020-05-22 09:15
 */
public class ThreadCoordinationTest {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private final Object object = new Object();

    public static void main(String[] args) {
        ThreadCoordinationTest threadCoordinationTest = new ThreadCoordinationTest();
        threadCoordinationTest.test();
    }

    public void test() {

        ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(3, 3, "test");

        threadPoolExecutor.submit(() -> {
            while (true) {
                synchronized (object) {
                    while (atomicInteger.get() % 3 != 0) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (atomicInteger.get() <= 99) {
                        System.out.println(Thread.currentThread().getName() + "==" + atomicInteger.getAndIncrement());
                        object.notifyAll();
                    }
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            while (true) {
                synchronized (object) {
                    while (atomicInteger.get() % 3 != 1) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (atomicInteger.get() <= 99) {
                        System.out.println(Thread.currentThread().getName() + "==" + atomicInteger.getAndIncrement());
                        object.notifyAll();
                    }
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            while (true) {
                synchronized (object) {
                    while (atomicInteger.get() % 3 != 2) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (atomicInteger.get() <= 99) {
                        System.out.println(Thread.currentThread().getName() + "==" + atomicInteger.getAndIncrement());
                        object.notifyAll();
                    }
                }
            }
        });

    }
}
