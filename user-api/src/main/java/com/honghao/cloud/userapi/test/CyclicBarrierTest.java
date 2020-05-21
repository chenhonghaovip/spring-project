package com.honghao.cloud.userapi.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenhonghao
 * @date 2019-12-18 13:40
 */
public class CyclicBarrierTest {

    static CyclicBarrier cyclicBarrier;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(true);
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        //当所有子任务都执行完毕时,barrierAction的run方法会被调用
        cyclicBarrier = new CyclicBarrier(1, () ->{
            System.out.println("执行barrierAction操作!");
            atomicBoolean.set(true);
        }
                );
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,2,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
        //开启多个线程执行子任务
       while (true){
           if (atomicBoolean.getAndSet(false) && atomicInteger.getAndIncrement()<100){
               threadPoolExecutor.submit(()->doSelect(cyclicBarrier, atomicInteger.get()));
           }
       }

    }

    private static void doSelect(CyclicBarrier cyclicBarrier,int taskNum){
        //执行子任务
        System.out.println("子任务："+taskNum+" 执行完毕!");
        try {
            //等待所有子任务执行完成
            cyclicBarrier.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
