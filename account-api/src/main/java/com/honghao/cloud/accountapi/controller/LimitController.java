package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.accountapi.service.LimitService;
import com.honghao.cloud.basic.common.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 服务限流
 *
 * @author chenhonghao
 * @date 2021-01-21 15:26
 */
@RestController
@RequestMapping("/limitController")
public class LimitController {
    @Resource
    private LimitService limitService;

    /**
     * 单机简单滑动窗口
     *
     * @return BaseResponse
     */
    @GetMapping("/singleMachineSlidingWindow")
    public BaseResponse singleMachineSlidingWindow() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                limitService.singleMachineSlidingWindow();
            }).start();
        }
        return BaseResponse.success();
    }

    /**
     * 简单令牌桶
     *
     * @return BaseResponse
     */
    @GetMapping("/singleTokenBucket")
    public BaseResponse singleTokenBucket() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                limitService.singleTokenBucket();
            }).start();
        }
        return BaseResponse.success();
    }


    /**
     * 简单令牌桶
     *
     * @return BaseResponse
     */
    @GetMapping("/redisTokenBucket")
    public BaseResponse redisTokenBucket() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                limitService.singleTokenBucket();
            }).start();
        }
        return BaseResponse.success();
    }

}
