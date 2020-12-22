package com.honghao.cloud.userapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author chenhonghao
 * @date 2019-07-20 12:15
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    private static final String SIMPLE_POOL = "asyncPool";

    @Bean(SIMPLE_POOL)
    public Executor simplePool() {
        return buildPool(SIMPLE_POOL, 5, 10, 200);
    }

    /**
     * 构建 线程池
     *
     * @param name     线程名称
     * @param corePool 核心池
     * @param maxPool  最大数量
     * @param capacity 容量
     * @return Executor
     */
    private Executor buildPool(String name, int corePool, int maxPool, int capacity) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(capacity);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix(name + "-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
