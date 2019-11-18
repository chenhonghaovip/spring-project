package com.honghao.cloud.orderapi.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试线程池配置
 *
 * @author chenhonghao
 * @date 2019-11-16 16:47
 */
public class TestThreadPoolConfig {
    private static final String prx = "test";
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static ThreadPoolExecutor create(int corePoolSize,int maximumPoolSize){
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50), r -> new Thread(r,prx));
    }
}
