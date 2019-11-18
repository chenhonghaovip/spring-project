package com.honghao.cloud.orderapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 初始化业务线程池配置  business
 *
 * @author chenhonghao
 * @date 2019-07-26 14:54
 */
@Slf4j
@Component
public class ThreadPoolInitConfig {
    private static AtomicInteger atomicInteger=new AtomicInteger(0);

    private static ThreadPoolExecutor build(String name){
        String namePrefix = "ThreadPool-" +
                name + atomicInteger.getAndIncrement();
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,namePrefix);
            }
        });
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    @PostConstruct
    void getInfo(){
        Long startTime = System.currentTimeMillis();
        log.info("start time is :{}",startTime);
        ThreadPoolExecutor executor= ThreadPoolInitConfig.build("create");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "xing is chen";
        },executor);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "name is honghao";
        },executor);

        CompletableFuture.allOf(future, future1)
                .whenCompleteAsync((aVoid, throwable) -> {
                    Long end = System.currentTimeMillis();
                    log.info("end time is :{}",end);
                    log.info("user time:{}",end-startTime);
                    String name= null;
                    String xing= null;
                    try {
                        name = future1.get();
                        xing = future.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name+" and "+xing);
                },executor);
    }
}
