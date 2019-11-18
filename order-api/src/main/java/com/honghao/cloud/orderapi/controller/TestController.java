package com.honghao.cloud.orderapi.controller;

import com.honghao.cloud.orderapi.base.BaseResponse;
import com.honghao.cloud.orderapi.config.TestThreadPoolConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 测试
 *
 * @author chenhonghao
 * @date 2019-11-16 17:01
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/test01")
    BaseResponse<String> test01(@RequestBody String data){
        ThreadPoolExecutor threadPoolExecutor = TestThreadPoolConfig.create(1,5);
        threadPoolExecutor.execute(() -> System.out.println("111111111"));

        return BaseResponse.successData("123456");
    }
}
