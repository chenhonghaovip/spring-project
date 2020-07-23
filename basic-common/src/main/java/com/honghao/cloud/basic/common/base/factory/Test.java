package com.honghao.cloud.basic.common.base.factory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenhonghao
 * @date 2020-07-23 15:39
 */
public class Test {
    static String preName = "test";

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new NamedThreadFactory(preName, false), new MyRejectedExecutionHandler());
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(()->{
                System.out.println("12313");
            });
        }
    }
}
