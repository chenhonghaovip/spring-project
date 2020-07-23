package com.honghao.cloud.basic.common.base.factory;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工厂
 *
 * @author chenhonghao
 * @date 2020-03-26 21:46
 */
@Slf4j
public class ExecutorFactory {

    public static ThreadPoolExecutor buildThreadPoolExecutor(int core,int max,String preName){
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return new ThreadPoolExecutor(core, max, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), r -> new Thread(r,preName+atomicInteger.incrementAndGet()),new MyRejectedExecutionHandler());
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int core,int max,int queueSize ,String preName){
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return new ThreadPoolExecutor(core, max, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize), r -> new Thread(r,preName+atomicInteger.incrementAndGet()),new MyRejectedExecutionHandler());
    }

}
