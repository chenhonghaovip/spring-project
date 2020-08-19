package com.honghao.cloud.accountapi;

import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2020-08-05 20:26
 */
public class Test {
    private static Random random = new Random();
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(10,100,"hongbao");
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static int num = 10;
    private static int money = 100;
    private static BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        money = money*100;
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(()->{
                reentrantLock.lock();
                try {
                    if (num==1){
                        blockingQueue.add(money);
                        return;
                    }

                    int a = random.nextInt(money/num*2);
                    while (money-a < num-1 || a<1){
                        a = random.nextInt(money/num*2);
                    }
                    money-=a;
                    num--;
                    blockingQueue.add(a);
                } finally {
                    reentrantLock.unlock();
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        BigDecimal basic = BigDecimal.valueOf(100);
        int s = blockingQueue.size();
        for (int i = 0; i < s; i++) {
            int person = blockingQueue.poll();
            System.out.println(new BigDecimal(person).divide(basic));
        }
    }
}
