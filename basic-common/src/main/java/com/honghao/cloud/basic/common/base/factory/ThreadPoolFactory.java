package com.honghao.cloud.basic.common.base.factory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工厂
 *
 * @author chenhonghao
 * @date 2020-03-26 21:46
 */
public class ThreadPoolFactory {

    public static ThreadPoolExecutor buildThreadPoolExecutor(int core,int max,String preName){
        return new ThreadPoolExecutor(core, max, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new NamedThreadFactory(preName,10),new MyRejectedExecutionHandler());
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int core,int max,int queueSize ,String preName){
        return new ThreadPoolExecutor(core, max, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize), new NamedThreadFactory(preName,10),new MyRejectedExecutionHandler());
    }

    public static ScheduledThreadPoolExecutor buildScheduledThreadPoolExecutor(int core, String preName){
        return new ScheduledThreadPoolExecutor(core, new NamedThreadFactory(preName,10),new MyRejectedExecutionHandler());
    }

    public static ScheduledThreadPoolExecutor buildScheduledThreadPoolExecutor(int core){
        return new ScheduledThreadPoolExecutor(core, new MyRejectedExecutionHandler());
    }



}
