package com.honghao.cloud.userapi.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 初始化业务线程池配置  business
 *
 * @author chenhonghao
 * @date 2019-07-26 14:54
 */
@Slf4j
public class ThreadPoolInitConfig {
    private static final AtomicInteger atomicInteger=new AtomicInteger(0);

    public static ThreadPoolExecutor build(String name){
        String namePrefix = "ThreadPool-" +
                name + atomicInteger.getAndIncrement();
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2,10,60, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        threadPoolExecutor.setThreadFactory(r -> {
            Thread t = new Thread(r,namePrefix);
            return t;
        });
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }
}
