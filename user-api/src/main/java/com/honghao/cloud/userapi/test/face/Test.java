package com.honghao.cloud.userapi.test.face;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenhonghao
 * @date 2020-02-05 17:03
 */
public class Test {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,11, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));

        Random random = new Random();

        while (true){
            CountDownLatch countDownLatch = new CountDownLatch(1);
            CountDownLatch countDownLatch1 = new CountDownLatch(1);

            threadPoolExecutor.execute(() -> {
                System.out.println(random.nextInt()+'a');
                countDownLatch.countDown();
            });

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            threadPoolExecutor.execute(() -> {
                System.out.println(random.nextInt(9));
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
