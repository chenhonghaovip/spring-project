package com.honghao.cloud.leetcode.algorithm.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * 排序算法测试01
 *
 * @author chenhonghao
 * @date 2019-09-22 10:52
 */
@Slf4j
public class SortTest01 {
    public static void main(String[] args) {
        int[] arr = new int[]{2,3,1,4,5,2,34,1,43};
        int temp ;
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length-i-1; j++) {
                if (arr[j] > arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        log.info("{}",arr);
    }
}
