package com.honghao.cloud.orderapi.common.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 *
 * @author chenhonghao
 * @date 2020-03-25 10:22
 */
public class ThreadPoolFactory implements ThreadFactory {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    private static String namePrefix;

    @Override
    public Thread newThread(Runnable r) {
        return null;
    }
}
