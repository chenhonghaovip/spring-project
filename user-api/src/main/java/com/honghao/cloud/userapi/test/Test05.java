package com.honghao.cloud.userapi.test;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chenhonghao
 * @date 2019-12-17 15:54
 */
@Slf4j
public class Test05 {
    private static int num = 0;
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10,20, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(()-> get(reentrantLock));
        }
        threadPoolExecutor.shutdown();

        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        reentrantReadWriteLock.readLock().lock();
        reentrantReadWriteLock.writeLock().lock();
        reentrantReadWriteLock.readLock().unlock();
        reentrantReadWriteLock.writeLock().unlock();
    }

    public static void get(ReentrantLock reentrantLock){
        reentrantLock.lock();
        num++;
        System.out.println(Thread.currentThread().getName() + "      "+num);
        reentrantLock.unlock();
    }

}
