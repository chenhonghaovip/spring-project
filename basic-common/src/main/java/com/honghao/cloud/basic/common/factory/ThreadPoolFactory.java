package com.honghao.cloud.basic.common.factory;

import java.util.concurrent.*;

/**
 * 线程池工厂
 *
 * @author chenhonghao
 * @date 2020-03-26 21:46
 */
public class ThreadPoolFactory {

    public static ThreadPoolExecutor buildThreadPoolExecutor(int core, int max, String preName, BlockingQueue<Runnable> blockingQueue,RejectedExecutionHandler rejectedExecutionHandler){
        return new ThreadPoolExecutor(core, max, 60, TimeUnit.SECONDS, blockingQueue, new NamedThreadFactory(preName,max),rejectedExecutionHandler);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int core,int max,String preName){
        return new ThreadPoolExecutor(core, max, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new NamedThreadFactory(preName,max),new MyRejectedExecutionHandler());
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int core,int max,int queueSize ,String preName){
        return new ThreadPoolExecutor(core, max, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize), new NamedThreadFactory(preName,max),new MyRejectedExecutionHandler());
    }

    public static ScheduledThreadPoolExecutor buildScheduledThreadPoolExecutor(int core, String preName){
        return new ScheduledThreadPoolExecutor(core, new NamedThreadFactory(preName,10),new MyRejectedExecutionHandler());
    }

    public static ScheduledThreadPoolExecutor buildScheduledThreadPoolExecutor(int core){
        return new ScheduledThreadPoolExecutor(core, new MyRejectedExecutionHandler());
    }



}
