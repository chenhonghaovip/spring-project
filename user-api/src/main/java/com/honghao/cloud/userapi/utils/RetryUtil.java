package com.honghao.cloud.userapi.utils;

import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;


/**
 * 重试策略
 *
 * @author chenhonghao
 * @date 2020-03-25 15:17
 */
@Slf4j
public class RetryUtil {

        public static <T> T retry(Callable<T> task, Predicate<T> predicate, long fixedWaitTime, TimeUnit timeUnit, int attemptNumber) {
            Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                    .retryIfResult(predicate::test)
                    .retryIfException()
                    .withWaitStrategy(WaitStrategies.fixedWait(fixedWaitTime, timeUnit))
                    .withStopStrategy(StopStrategies.stopAfterAttempt(attemptNumber))
                    .build();
            T call = null;
            try {
                call = retryer.call(task);
            } catch (ExecutionException | RetryException e) {
                log.error("请求失败");
            }
            return call;
        }
}
