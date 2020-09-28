package com.honghao.cloud.accountapi.controller;


import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.accountapi.facade.AccountFacade;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * 用户信息controller
 *
 * @author chenhonghao
 * @date 2019-07-17 17:22
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Api(value = "用户接口服务",tags = "账户服务")
public class AccountController {
    private static ThreadPoolExecutor executor = ThreadPoolFactory.buildThreadPoolExecutor(1,1,"httpHystrix",new SynchronousQueue<>(),new ThreadPoolExecutor.AbortPolicy());
    @Resource
    private AccountFacade orderFacade;

    @PostMapping("/create")
    @ApiOperation(value = "创建订单",notes = "创建订单")
    public BaseResponse createUser(@RequestParam String data) {
        return BaseResponse.successData(LocalDateTime.now());
    }

    /**
     * 线程资源隔离，简单模仿hystrix原理来实现
     * @return BaseResponse
     */
    @PostMapping("/hystrixThread")
    @ApiOperation(value = "创建订单",notes = "创建订单")
    public BaseResponse hystrixThread() {
        Future<ShopInfo> submit = executor.submit(() -> {
            ShopInfo shopInfo = new ShopInfo();
            Thread.sleep(10000);
            return shopInfo;
        });
        Object o;
        try {
            o = submit.get();
        } catch (InterruptedException | ExecutionException e) {
            return BaseResponse.error(e.getMessage());
        }
        return BaseResponse.successData(o);
    }

    /**
     * 简单模拟并发情况下的线程池资源隔离技术
     */
    @Test
    public void test(){
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(hystrixThread());
                countDownLatch.countDown();
            }).start();

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
