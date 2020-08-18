package com.honghao.cloud.accountapi;

import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2020-08-05 20:26
 */
public class Test {
    private static Random random = new Random();
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(10,100,"hongbao");
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static int num = 10;
    private static int money = 100;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(()->{
                reentrantLock.lock();
                try {
                    if (num==1){
                        System.out.println(money);
                        return;
                    }

                    int a = random.nextInt(money/num*3);
                    while (money-a < num-1 || a<1){
                        a = random.nextInt(money/num*3);
                    }
                    money-=a;
                    num--;
                    System.out.println(a);
                } finally {
                    reentrantLock.unlock();
                }
            });
        }
    }
}
