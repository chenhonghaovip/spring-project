package com.honghao.cloud.userapi;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.controller.DeliveryController;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.facade.BatchFacade;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * MVC形式调用
 *
 * @author chenhonghao
 * @date 2019-07-26 09:45
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApiApplication.class)
public class MvcTest {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000,1200,20, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
    @Resource
    private MessageSender messageSender;
    @Resource
    private BatchFacade batchFacade;
    @Resource
    private DeliveryController deliveryController;
    @Test
    public void test02(){
        log.info("开始接口测试工作");
    }

    @Test
    public void test(){
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                try {
                    countDownLatch.await();
                    BaseResponse<WaybillBcList> response = batchFacade.queryCommon(String.valueOf(finalI));
                    System.out.println("请求结果为"+response.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown();
        }
    }

    @Test
    public void pipelineTest(){
        Jedis jedis = new Jedis("49.235.212.2",6379);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            jedis.set(String.valueOf(i),String.valueOf(i));
        }
        System.out.println("普通jedis操作:"+(System.currentTimeMillis()-startTime));

        Pipeline pipeline = jedis.pipelined();

        long secondTime = System.currentTimeMillis();
        for (int i = 10000; i < 200000; i++) {
            pipeline.set(String.valueOf(i),String.valueOf(i));
        }
        pipeline.sync();
        System.out.println("pipeline操作:"+(System.currentTimeMillis()-secondTime));
    }
    @Test
    public void test03(){
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                try {
                    countDownLatch.await();
                    BaseResponse<WaybillBcList> response = batchFacade.queryCommon(String.valueOf(finalI));
                    System.out.println("请求结果为"+response.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown();
        }
    }

}
