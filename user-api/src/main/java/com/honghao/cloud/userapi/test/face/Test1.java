package com.honghao.cloud.userapi.test.face;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chenhonghao
 * @date 2020-02-05 17:03
 */
public class Test1 {
    public static void main(String[] args) {
        AtomicBoolean flag = new AtomicBoolean(true);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,11, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
        while (true) {
            if (flag.get()) {
                flag.set(false);
                CyclicBarrier cyclicBarrier = new CyclicBarrier(1,
                        ()-> {
                            System.out.println("1");
                            flag.set(true);
                        });
                threadPoolExecutor.execute(() -> {
                    System.out.println("a");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                });
            }
        }
    }
}
