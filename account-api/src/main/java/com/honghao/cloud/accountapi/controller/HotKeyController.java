package com.honghao.cloud.accountapi.controller;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
    private final LoadingCache<String, String> readWriteCacheMap = CacheBuilder.newBuilder().initialCapacity(1000)
            .expireAfterWrite(180, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return cacheMap.get(key);
                }
            });

    @Test
    public void tesst(){
        for (int i = 0; i < 10; i++) {
            String s = String.valueOf(i);
            cacheMap.put(s,s);
        }
        try {
            for (String s : cacheMap.keySet()) {
                System.out.println(readWriteCacheMap.get(s));
            }
            String s = readWriteCacheMap.get("2");
            System.out.println(s);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
