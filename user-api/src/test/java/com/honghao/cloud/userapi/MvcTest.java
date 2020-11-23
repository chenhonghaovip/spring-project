package com.honghao.cloud.userapi;

import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.facade.BatchFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private BatchFacade batchFacade;
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
    public void test03(){
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                try {
                    countDownLatch.countDown();
                    countDownLatch.await();
                    BaseResponse<WaybillBcList> response = batchFacade.queryCommon1(String.valueOf(finalI));
                    System.out.println("请求结果为"+response.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
