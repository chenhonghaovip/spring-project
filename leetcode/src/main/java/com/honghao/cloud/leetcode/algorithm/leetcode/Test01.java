package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试一
 *
 * @author chenhonghao
 * @date 2019-10-05 13:10
 */
@Slf4j
public class Test01 {
    public static void main(String[] args) {
        int sum = 15;
        int[] num = {1, 2, 3, 4, 5, 6, 7, 8};

        Map map = new HashMap();
        for (int i = 0; i < num.length; i++) {
            map.put(num[i], i);
        }

        for (int i = 0; i < num.length; i++) {
            int target = sum - num[i];
            if (map.containsKey(target)) {
                log.info("数组中位置为{}，{}", i, map.get(target));
                return;
            }
        }
    }
}
