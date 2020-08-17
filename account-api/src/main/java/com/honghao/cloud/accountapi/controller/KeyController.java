package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2020-08-17 14:49
 */
@RestController
@RequestMapping("/keyController")
public class KeyController {
    private static int thirdValue = 0;
    private static long secondValue = 0L;
    private static int secondCapacity = 10000;
    private static LocalDate today = LocalDate.now();
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static String firstValue = today.format(dateTimeFormatter);
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(1000,10000,"usfas");

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 封装keyId,8位日期，4位redis生成，4位各自服务填充
     * @return BaseResponse
     */
    @GetMapping("/primaryKeyGeneration")
    public BaseResponse<String> primaryKeyGeneration() {
        LocalDate now = LocalDate.now();
        reentrantLock.lock();
        try {
            // 换日之后，重新计数，第一部分改为当前日期，第二部分从0001开始，第三部分各自服务从0000到9999
            if (!now.isEqual(today)){
                redisTemplate.delete(firstValue);
                today = now;
                firstValue = now.format(dateTimeFormatter);
                secondValue = redisTemplate.opsForValue().increment(firstValue);
                thirdValue = -1;
            }
            // 本地生成的第三部分达到9999条之后，第二部分重新生成
            if (++thirdValue>=secondCapacity || secondValue == 0L){
                secondValue = redisTemplate.opsForValue().increment(firstValue);
                thirdValue = 0;
            }

            String key = firstValue + String.format("%04d", secondValue)+String.format("%04d", thirdValue);
            return BaseResponse.successData(key);
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * 封装keyId,8位日期，4位redis生成，4位各自服务填充
     * @return BaseResponse
     */
    @GetMapping("/test")
    public BaseResponse test() {
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        Set<String> set = new HashSet<>();
        long l = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            threadPoolExecutor.execute(()-> {
                countDownLatch.countDown();
                BaseResponse<String> response = primaryKeyGeneration();
                System.out.println(response.getData());
                set.add(response.getData());
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("历时："+(System.currentTimeMillis()-l));
        return BaseResponse.success();
    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusDays(1);
        System.out.println(now.isEqual(localDate));
    }

}
