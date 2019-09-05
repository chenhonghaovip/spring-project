package com.honghao.cloud.orderapi.algorithm;

import lombok.extern.slf4j.Slf4j;

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
    }
}
