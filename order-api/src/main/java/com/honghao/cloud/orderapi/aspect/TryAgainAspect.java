package com.honghao.cloud.orderapi.aspect;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.base.RetryException;
import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import com.honghao.cloud.orderapi.listener.rabbitmq.producer.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 重试切面处理
 *
 * @author chenhonghao
 * @date 2020-09-29 11:57
 */
@Slf4j
@Aspect
@Component
public class TryAgainAspect implements InitializingBean {
    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1);
    private static final ConcurrentHashMap<String, LoadingCache<String, SlidingWindow>> CACHE_MAP = new ConcurrentHashMap<>();
    private static final LoadingCache<String, Boolean> LOADING_CACHE = CacheBuilder.newBuilder().initialCapacity(1000)
            .expireAfterWrite(180, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Boolean>() {
                @Override
                public Boolean load(String key) {
                    return false;
                }
            });

    @Resource
    private MessageSender messageSender;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(TryAgain)")
    public void aspect() {
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取拦截的方法名
        MethodSignature msig = (MethodSignature) joinPoint.getSignature();
        // 返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        // 为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        // 获取设置的乐观锁最大重试次数
        TryAgain annotation = currentMethod.getAnnotation(TryAgain.class);
        int maxValue = annotation.value();

        int retryTimes = 0;
        do {
            log.info("msg:--------------------" + Thread.currentThread().getName());
            retryTimes++;
            try {
                return joinPoint.proceed();
            } catch (RetryException e) {
                log.error(e.getErrorMsg());
            }
        } while (maxValue >= retryTimes);
        log.error("乐观锁重试失败");
        return BaseResponse.error();
    }


    @Pointcut("@annotation(TimeOut)")
    public void timeOutAspect() {
    }

    @Around("timeOutAspect()")
    public Object aroundTimeOut(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取拦截的方法名
        MethodSignature msig = (MethodSignature) joinPoint.getSignature();
        // 返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        // 为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        // 获取设置的乐观锁最大重试次数
        TimeOut timeOut = currentMethod.getAnnotation(TimeOut.class);
        int time = timeOut.time();
        String key = timeOut.key();
        String queue = timeOut.queue();

        // 判断当前url是否处于熔断状态，如果处于，则进行持久化后当作消费完成，后期进行补偿操作
        String url = (String) joinPoint.getArgs()[0];
        if (LOADING_CACHE.get(url)) {
            return BaseResponse.error("服务熔断处理");
        }

        long now = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (RetryException e) {
            log.error(e.getErrorMsg());
            return BaseResponse.error(e.getErrorMsg());
        } finally {
            long end = System.currentTimeMillis();
            if (end - now > time * 1000) {
                // 需要记录当前接口超时，加入到计数器中去
                test(url);
            }
        }
    }

    private void test(String url) {
        LoadingCache<String, SlidingWindow> loadingCache;
        if (Objects.isNull(loadingCache = CACHE_MAP.get(url))) {
            loadingCache = CacheBuilder.newBuilder().initialCapacity(1000)
                    .expireAfterWrite(180, TimeUnit.SECONDS)
                    .build(new CacheLoader<String, SlidingWindow>() {
                        @Override
                        public SlidingWindow load(String key) {
                            // 设置滑动窗口设置为1分钟累计10个，超过之后就进行熔断处理
                            return new SlidingWindow(60, 10);
                        }
                    });
            CACHE_MAP.putIfAbsent(url, loadingCache);
        }
        try {
            SlidingWindow slidingWindow = loadingCache.get(url);
            // 加1并且判断是否达到阈值
            boolean b = slidingWindow.addCount(1);
            if (!b) {
                // 未达到阈值，继续累加操作
                loadingCache.put(url, slidingWindow);
            } else {
                // 如果达到阈值，将其加入到过期队列中，等待过期后才可以开始访问，否则继续正常执行
                LOADING_CACHE.put(url, true);
                // TODO: 2020/12/15 持久化到缓存中，后面会扫描使用
                redisTemplate.opsForList().rightPush(url, "");
                // 删除缓存中的窗口信息
                loadingCache.invalidate(url);
            }

        } catch (ExecutionException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() {
        SCHEDULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(() -> LOADING_CACHE.asMap().entrySet().stream().filter(m -> !m.getValue()).forEach(each -> {
            Object o;
            while (Objects.nonNull(o = redisTemplate.opsForList().leftPop(each.getKey()))) {
                messageSender.publicQueueProcessing(o, "");
            }
        }), 5, 5, TimeUnit.MINUTES);
    }
}
