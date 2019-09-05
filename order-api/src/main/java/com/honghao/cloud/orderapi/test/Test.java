package com.honghao.cloud.orderapi.test;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenhonghao
 * @date 2019-07-17 09:23
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        int m;
        try {
            m = 10/0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ccccccccccccc");

            String[] str1={"a","b","c","d","e"};
            String[] str2={"d","e","f","g","h"};
            Set<String> set1 = new HashSet<>(Arrays.asList(str1));
            Set<String> set2 = new HashSet<>(Arrays.asList(str2));
            Set<String> set = new HashSet<>();
            // 并集
            set.addAll(set1);
            set.addAll(set2);
            System.out.println("并集" + set);

            // 交集
            set.clear();
            set.addAll(set1);
            set.retainAll(set2);
            System.out.println("交集" + set);
            // 差集
            set.clear();
            set.addAll(set1);
            set.removeAll(set2);
            System.out.println("差集" + set);
            AtomicInteger atomicInteger = new AtomicInteger(0);

            for (int i =1;i < 100;i++){
                    atomicInteger.incrementAndGet();
                    if (atomicInteger.compareAndSet(2,5)) {
                            log.info("{}",atomicInteger.get());
                            break;
                    }else {
                            log.info("{}",atomicInteger.get());
                    }
            }



    }
}
