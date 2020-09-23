package com.honghao.cloud.accountapi.component;

import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * redis超时处理
 *
 * @author chenhonghao
 * @date 2020-09-03 16:27
 */
@Slf4j
@Component
public class RedisTimeOut {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(1,1,"init");
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void init(){
        threadPoolExecutor.execute(()->{
            while (true){
                final String key = "order:time:out";
                Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().rangeWithScores(key, 0, 0);
                if (set==null || set.isEmpty()){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                    continue;
                }
                for (ZSetOperations.TypedTuple<Object> tuple : set) {
                    double score = tuple.getScore()!=null?tuple.getScore():0;
                    long now = System.currentTimeMillis() / 1000;
                    if (now >= score){
                        Long remove = redisTemplate.opsForZSet().remove(key, tuple.getValue());
                        if(remove!=null && remove > 0){
                            System.out.println(tuple.getValue());
                            // TODO: 2020/9/3 do something
                        }
                    }
                }
            }
        });
    }
}
