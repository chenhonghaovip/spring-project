package com.honghao.cloud.accountapi.factory;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 自定义线程池(自己实现简单的线程池功能)
 *
 * @author chenhonghao
 * @date 2020-07-16 22:29
 */
@Slf4j
public class MyThreadPoolExecutor {
    private volatile int core;

    private volatile int taskSize;

    private volatile List<Thread> worker;

    private volatile BlockingQueue<Runnable> blockingQueue;


    public MyThreadPoolExecutor(int core, int taskSize) {
        if (core<=0 || taskSize<=0){
            throw new IllegalArgumentException("参数非法");
        }
        this.core = core;
        this.taskSize = taskSize;
        this.blockingQueue = new LinkedBlockingQueue<>(taskSize);
        this.worker = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < core; i++) {
            Worker worker = new Worker(this);
            worker.start();
            this.worker.add(worker);
        }
    }

    public static class Worker extends Thread{
        private MyThreadPoolExecutor myThreadPoolExecutor;

        public Worker(MyThreadPoolExecutor myThreadPoolExecutor) {
            this.myThreadPoolExecutor = myThreadPoolExecutor;
        }

        @Override
        public void run() {
            while (true){
                Runnable runnable = null;
                try {
                    runnable = this.myThreadPoolExecutor.blockingQueue.poll();
                } catch (Exception e) {
                   log.error(e.getMessage());
                }
                if (Objects.nonNull(runnable)){
                    runnable.run();
                }
            }
        }
    }

    public void execute(Runnable runnable){
        this.blockingQueue.offer(runnable);
    }





}
