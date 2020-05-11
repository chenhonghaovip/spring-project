package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.factory.ExecutorFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenhonghao
 * @date 2020-05-11 21:55
 */
public class Test {
    private static ThreadPoolExecutor parent = ExecutorFactory.buildThreadPoolExecutor(10,100,"odoofd");
    private static ThreadPoolExecutor child = ExecutorFactory.buildThreadPoolExecutor(10,100,"odoofd");
//    private static CyclicBarrier cyclicBarrier;
   static CountDownLatch countDownLatch = new CountDownLatch(500);
    @org.junit.Test
    public void test(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(500);

        AtomicInteger atomicInteger = new AtomicInteger(1);
        for (int i = 0; i < 1000000; i++) {
//            new CyclicBarrierThread(cyclicBarrier,atomicInteger.getAndIncrement()).run();
            test1(atomicInteger.getAndIncrement());
        }

    }

    private void test1(int i){

//        countDownLatch.countDown();
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("同步"+i);
        CompletableFuture.runAsync(()-> System.out.println("异步"+i),child);

    }

    private static class CyclicBarrierThread implements Runnable {
        CyclicBarrier cyclicBarrier;

        //任务序号
        int taskNum;

        private void test1(CyclicBarrier cyclicBarrier,int i){

                countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("同步"+i);
            CompletableFuture.runAsync(()-> System.out.println("异步"+i),child);

        }

        CyclicBarrierThread(CyclicBarrier cyclicBarrier,final int taskNum) {
            this.cyclicBarrier = cyclicBarrier;
            this.taskNum = taskNum;
        }

        @Override
        public void run() {
            test1(cyclicBarrier,taskNum);
        }
    }
}
