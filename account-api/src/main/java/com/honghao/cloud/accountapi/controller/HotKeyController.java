package com.honghao.cloud.accountapi.controller;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.honghao.cloud.basic.common.base.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 热点key探测控制器
 *
 * @author chenhonghao
 * @date 2020-10-21 10:59
 */
@RestController
@RequestMapping("/hotKey")
public class HotKeyController {
    private static Map<String,String> cacheMap = new HashMap<>(100);
//    private static final Cache<String,Object> CACHE = Caffeine.newBuilder()
//            .initialCapacity(256).maximumSize(50000).expireAfterWrite(5, TimeUnit.SECONDS)
////            .executor(executorService)
//            .softValues().build();

    private static final LoadingCache<String, String> LOADING_CACHE = CacheBuilder.newBuilder().initialCapacity(1000)
            .expireAfterWrite(180, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                    return cacheMap.get(key);
                }
            });


    @PostMapping("/addCache")
    public BaseResponse addCache(){
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try {
            System.out.println("111");
        } finally {
            reentrantLock.unlock();
        }

//        CACHE.getIfPresent()
        for (int i = 0; i < 10; i++) {
            String s = String.valueOf(i);
            cacheMap.put(s,s);
        }
        try {
            for (String s : cacheMap.keySet()) {
                System.out.println(LOADING_CACHE.get(s));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return BaseResponse.success();
    }

    @GetMapping("/queryCache")
    public BaseResponse queryCache(@RequestParam("key") String key){
        try {
            return BaseResponse.successData(LOADING_CACHE.get(key));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return BaseResponse.error();
    }
}
