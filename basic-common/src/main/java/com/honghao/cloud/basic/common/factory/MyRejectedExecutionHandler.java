package com.honghao.cloud.basic.common.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义拒绝策略
 *
 * @author chenhonghao
 * @date 2020-07-23 15:33
 */
public class MyRejectedExecutionHandler implements RejectedExecutionHandler {
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

        System.out.println("my info is :" + "Task " + r.toString() +
                " rejected from " +
                executor.toString());
//            executor.getQueue().take()
        throw new RejectedExecutionException("Task " + r.toString() +
                " rejected from " +
                executor.toString());

    }
}
