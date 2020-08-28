package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.accountapi.common.enums.ErrorCodeEnum;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步测试控制中心
 *
 * @author chenhonghao
 * @date 2020-08-27 15:47
 */
@Slf4j
@RestController
@RequestMapping("/asyncController")
@Api(value = "异步测试控制中心",tags = "异步测试控制中心")
public class AsyncController {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(10,50,"asyncTest");
    private static ConcurrentHashMap<String,DeferredResult<BaseResponse<String>>> concurrentHashMap = new ConcurrentHashMap<>();

    private static final Long TIMEOUTVALUE = 100000L;

    @GetMapping("/callable")
    public BaseResponse callable(){
        Callable<String> callable = () -> {
            Thread.sleep(30000);
            return "success";
        };
        return BaseResponse.success();
    }

    @GetMapping("/deferredResult")
    public DeferredResult<BaseResponse<String>> deferredResult(@RequestParam("userId") String userId){
        DeferredResult<BaseResponse<String>> deferredResult = new DeferredResult<>(TIMEOUTVALUE,BaseResponse.error(ErrorCodeEnum.NO_CHANGE_IN_DATA));
        if (Objects.nonNull(concurrentHashMap.putIfAbsent(userId,deferredResult))){
            deferredResult = concurrentHashMap.get(userId);
        }
        DeferredResult<BaseResponse<String>> finalDeferredResult = deferredResult;
        threadPoolExecutor.submit(()->{
            try {
                Thread.sleep(10000);
                finalDeferredResult.setResult(BaseResponse.successData("success"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        deferredResult.onTimeout(()-> log.error("调用超时"));
        deferredResult.onCompletion(()->log.info("调用完成"));
        return deferredResult;
    }
}
