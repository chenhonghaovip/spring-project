package com.honghao.cloud.userapi.test;

import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 重试策略测试
 *
 * @author chenhonghao
 * @date 2020-03-25 13:29
 */
@Slf4j
public class HelloDemo {
    private static Retryer<Boolean> retryer;
    static {
        retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(aBoolean -> false)
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();
    }
    public static void main(String[] args) {

        Callable<Boolean> callable = () -> {
            // do something useful here
            log.info("call...");
            throw new RuntimeException();
        };

        try {
            retryer.call(callable);
        } catch (RetryException | ExecutionException e) {
            log.error("try 3 time all error");
        }
//        Predicate<BaseResponse> predicate = baseResponse -> baseResponse.isResult();
//        RetryUtil.retry(callable, Predicates.alwaysTrue(),5,TimeUnit.SECONDS,3);

    }
}
