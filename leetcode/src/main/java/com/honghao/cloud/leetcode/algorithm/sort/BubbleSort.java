package com.honghao.cloud.leetcode.algorithm.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 冒泡排序算法
 *
 * @author chenhonghao
 * @date 2019-10-11 10:14
 */
@Slf4j
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = new int[]{253, 435, 53, 62, 213, 51, 423, 123, 12};
        int temp;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        log.info("{}", Arrays.stream(arr).filter(value -> value < 400).toArray());
    }
}
