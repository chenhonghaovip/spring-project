package com.honghao.cloud.accountapi.factory;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
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

    public static class MyRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (executor.isShutdown()) {
                return;
            }

            BlockingQueue<Runnable> workQueue = executor.getQueue();
            Runnable firstWork = workQueue.poll();
            boolean newTaskAdd = workQueue.offer(r);
            if (firstWork != null) {
                firstWork.run();
            }
            if (!newTaskAdd) {
                executor.execute(r);
            }

            System.out.println("my info is :"+"Task " + r.toString() +
                    " rejected from " +
                    executor.toString());
//            executor.getQueue().take()
            throw new RejectedExecutionException("Task " + r.toString() +
                    " rejected from " +
                    executor.toString());

        }
    }
}
