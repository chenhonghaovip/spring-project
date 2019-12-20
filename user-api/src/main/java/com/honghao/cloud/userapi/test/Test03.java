package com.honghao.cloud.userapi.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.graphbuilder.struc.Bag;
import com.honghao.cloud.userapi.dto.request.TestDTO;
import com.honghao.cloud.userapi.dto.request.Trader1;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2019-11-11 19:58
 */
@Slf4j
public class Test03 {
    private static final Unsafe unsafe = Unsafe.getUnsafe();

    public static void main(String[] args) {
        TestDTO testDTO = new TestDTO("chen",1, BigDecimal.valueOf(2.32),2.54);
        Set<TestDTO> set = new HashSet<>();
        set.add(testDTO);

        TestDTO testDTO1 = new TestDTO("chen",1, BigDecimal.valueOf(2.32),2.54);
        set.add(testDTO1);
        set.forEach(each -> System.out.println(JSON.toJSONString(each)) );

        System.out.println(testDTO.getClass()==testDTO1.getClass());
        System.out.println(testDTO.equals(testDTO1));

        Trader1 trader1 = new Trader1();
        Trader1 trader11 = new Trader1();
        System.out.println(trader1.equals(trader11));
        JSONObject jsonObject = new JSONObject();
        trader1 = JSONObject.toJavaObject(jsonObject,Trader1.class);

        Bag bag = new Bag();
        bag.add(1);
        HashMap<Integer,Integer> map = new HashMap<>(8);
        for (int i = 0; i < 8; i++) {
            map.put(100,i);
        }
        map.get(100);

        ConcurrentHashMap<Integer,Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(100,100);
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();


        int x = Integer.MAX_VALUE;
        int y = Integer.MAX_VALUE;
        int k = (x&y)+((x^y)>>1);
        log.info("ddddddd{}",k);

    }
}
