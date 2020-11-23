package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.accountapi.service.RedisService;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenhonghao
 * @date 2020-08-28 15:28
 */
@Slf4j
@RestController
@RequestMapping("/concurrent")
@Api(value = "并发服务",tags = "并发服务")
public class ConcurrentController {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(10000,20000,"ConcurrentController");
    @Resource
    private RedisService redisService;

    /**
     * 并发测试抢购，减少redis压力，做出的限流操作
     * @param userId userId
     * @return BaseResponse
     */
    @GetMapping("/redisConcurrent")
    public BaseResponse redisConcurrent(@RequestParam("userId") String userId){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(9999);
        for (int i = 0; i < 10000; i++) {
            threadPoolExecutor.submit(()->{
                try {
                    cyclicBarrier.await();
                    redisService.redisConcurrent(userId);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        return BaseResponse.success();
    }
}
