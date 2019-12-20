package com.honghao.cloud.userapi.test;

import java.util.concurrent.CyclicBarrier;

/**
 * @author chenhonghao
 * @date 2019-12-18 13:40
 */
public class CyclicBarrierTest {

    static CyclicBarrier cyclicBarrier;

    public static void main(String[] args) {
        int count = 10;
        //当所有子任务都执行完毕时,barrierAction的run方法会被调用
        cyclicBarrier = new CyclicBarrier(count, () ->
                System.out.println("执行barrierAction操作!"));
        //开启多个线程执行子任务
        for(int i=0;i<count;i++){
            new Thread(new CyclicBarrierThread(cyclicBarrier,i)).start();
        }

    }

    private static class CyclicBarrierThread implements Runnable {

        CyclicBarrier cyclicBarrier;

        //任务序号
        int taskNum;

        CyclicBarrierThread(CyclicBarrier cyclicBarrier, int taskNum) {
            this.cyclicBarrier = cyclicBarrier;
            this.taskNum = taskNum;
        }

        @Override
        public void run() {
            //执行子任务
            System.out.println("子任务："+taskNum+" 执行完毕!");
            try {
                //等待所有子任务执行完成
                cyclicBarrier.await();

            } catch (Exception e) {
                e.printStackTrace();
            }
            //释放资源
            System.out.println("线程："+taskNum+" 释放资源!");

        }
    }
}
