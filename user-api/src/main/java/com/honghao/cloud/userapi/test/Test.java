package com.honghao.cloud.userapi.test;

import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author chenhonghao
 * @date 2020-07-29 14:12
 */
public class Test {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(10,20,"test_countDowm");
    public static void main(String[] args) {
        InfoInterface test = new Info();
        countDownLatchTest();
        cyclicBarrierTest();
        semaphoreTest();
        exchangerTest();
        LogHandle logHandle = new LogHandle(test);
        InfoInterface o = (InfoInterface)Proxy.newProxyInstance(test.getClass().getClassLoader(), test.getClass().getInterfaces(), logHandle);
        o.info();

    }
    interface InfoInterface{
        String info();
    }
    static class Info implements InfoInterface{
        @Override
        public String info(){
            System.out.println("info");
            return "test";
        }
    }

    static class LogHandle implements InvocationHandler {
        private Object object;

        LogHandle(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("info before");
            Object invoke = method.invoke(object, args);
            System.out.println("info end");
            return invoke;
        }
    }
    /**
     * CountDownLatch使用
     */
    private static void countDownLatchTest() {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            final int i1 = i;
            threadPoolExecutor.execute(()->{
                System.out.println(Thread.currentThread().getName()+"=="+ i1);
                countDownLatch.countDown();
            });
        }

        try {
            System.out.println("sleep start");
            Thread.sleep(5000);
            System.out.println("sleep end and countDownLatch.await");
            countDownLatch.await();
            System.out.println("end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * CyclicBarrier使用
     */
    private static void cyclicBarrierTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4,()-> System.out.println("end"));
        for (int i = 0; i < 4; i++) {
            final int i1 = i;
            threadPoolExecutor.execute(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+"==start"+ i1);
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName()+"==end"+ i1);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 信号量使用
     */
    private static void semaphoreTest() {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 1; i < 5; i++) {
            final int i1 = i;
            threadPoolExecutor.execute(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+"==start"+ i1);
                    Thread.sleep(1000);
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"==end"+ i1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName()+"==release"+ i1);
                }
            });
        }
    }

    /**
     * Exchanger使用
     */
    private static void exchangerTest() {
        Exchanger<List<Integer>> exchanger = new Exchanger<>();
        for (int i = 1; i < 3; i++) {
            final int i1 = i;
            threadPoolExecutor.execute(()->{
                List<Integer> list;
                if (i1==1){
                    list = Arrays.asList(0, 2, 4, 6, 8);
                }else {
                    list = Arrays.asList(1, 3, 5, 7, 9);
                }
                try {
                    list = exchanger.exchange(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"=="+list);
            });
        }
    }
}
