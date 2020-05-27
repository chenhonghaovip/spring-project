package com.honghao.cloud.userapi.test.face;

import com.honghao.cloud.userapi.factory.ExecutorFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chenhonghao
 * @date 2020-02-05 17:03
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = ExecutorFactory.buildThreadPoolExecutor(2,2,"test_");
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        while (true){
            if (atomicBoolean.getAndSet(false)){
                CountDownLatch countDownLatch = new CountDownLatch(1);
                CountDownLatch countDownLatch1 = new CountDownLatch(1);

                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"11111");
                    countDownLatch.countDown();
                });

                threadPoolExecutor.execute(() -> {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                    System.out.println(Thread.currentThread().getName()+"aaaa");
                    atomicBoolean.set(true);
                    countDownLatch1.countDown();
                });
                try {
                    countDownLatch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
