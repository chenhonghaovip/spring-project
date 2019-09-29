package com.honghao.cloud.orderapi.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 算法测试01
 *
 * @author chenhonghao
 * @date 2019-08-30 15:45
 */
@Slf4j
public class Test01 {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        int sum = 0;
        int n = 1000000;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        long middleTime = System.currentTimeMillis();
        log.info("时间为：{}，结果为：{}",middleTime-time ,sum);

        long num = (1 + n) * n ;
        long endTime = System.currentTimeMillis();
        log.info("时间为：{}，结果为：{}",endTime - middleTime ,num);

        BigDecimal bigDecimal = BigDecimal.valueOf(1);
        BigDecimal bigDecimal1 = BigDecimal.valueOf(2);
        log.info(":{}",bigDecimal.equals(bigDecimal1));
        log.info("{}",bigDecimal.add(bigDecimal1));
    }
}
