package com.honghao.cloud.userapi.test;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.dto.request.Trader;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chenhonghao
 * @date 2019-11-11 19:58
 */
@Slf4j
public class Test04 {
    private static final Unsafe UNSAFE;
    private static final Field FIELD;
    private static int num ;

    private static final long VALUE_OFFSET;
    static {
        try {
            FIELD = Unsafe.class.getDeclaredField("theUnsafe");
            FIELD.setAccessible(true);
            UNSAFE = (Unsafe) FIELD.get(null);
            VALUE_OFFSET = UNSAFE.objectFieldOffset
                    (Trader.class.getDeclaredField("name"));
        } catch (Exception ex) { throw new Error(ex); }
    }
    public static void main(String[] args) {
        Trader trader = new Trader("name","city");
        boolean flag = UNSAFE.compareAndSwapObject(trader, VALUE_OFFSET,"name","name1");
        log.info(JSON.toJSONString(trader));

        ReentrantLock reentrantLock = new ReentrantLock();
        if (reentrantLock.tryLock()){
            reentrantLock.lock();
            System.out.println("111111");
            reentrantLock.unlock();
        }



        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,10,20, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));

        List<CompletableFuture> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
           list.add(CompletableFuture.supplyAsync(()-> incream(),threadPoolExecutor));
        }
        CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()])).whenComplete((r,t)-> System.out.println(num));
        threadPoolExecutor.shutdown();
        //栅栏
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Test04 test04 = new Test04();
        test04.get();

    }

    private static int incream(){
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        reentrantReadWriteLock.writeLock().lock();
        int k = ++num;
        reentrantReadWriteLock.writeLock().unlock();
        reentrantReadWriteLock.readLock().lock();
        return k;
    }
    private void get(){

        System.out.println(111);
    }
}
