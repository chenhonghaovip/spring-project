package com.honghao.cloud.userapi.test.redis;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.utils.JedisOperator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 缓存穿透  -- 使用布隆过滤器减少无效请求
 * bloomfilter就类似于一个hash set，用于快速判某个元素是否存在于集合中，
 * 其典型的应用场景就是快速判断一个key是否存在于某容器，不存在就直接返回。
 * 布隆过滤器的关键就在于hash算法和容器大小
 *
 * @author chenhonghao
 * @date 2020-02-13 11:10
 */
@Slf4j
@RequestMapping("/bloomFilterTest")
@RestController
public class BloomFilterTest {
    private static final int capacity = 1000000;
    private static final int key = 999998;
    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), capacity);

    static {
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(String.valueOf(i));
        }
    }

    @Resource
    private JedisOperator jedisOperator;

    @ApiOperation("布隆过滤测试")
    @GetMapping("/bloomFilter")
    public BaseResponse<Boolean> bloomFilter(@RequestParam("key") String key){
        long start = System.nanoTime();

        if (bloomFilter.mightContain(key)) {
            System.out.println("成功过滤到" + key);
        }
        long end = System.nanoTime();
        System.out.println("布隆过滤器消耗时间:" + (end - start));
        int sum = 0;
        for (int i = capacity + 20000; i < capacity + 30000; i++) {
            if (bloomFilter.mightContain(String.valueOf(i))) {
                sum = sum + 1;
            }
        }
        System.out.println("错判率为:" + sum);
        String value = jedisOperator.get(key);
        return BaseResponse.successData(true);
    }
}
