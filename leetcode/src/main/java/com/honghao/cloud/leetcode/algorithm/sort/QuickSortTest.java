package com.honghao.cloud.leetcode.algorithm.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * 快速排序算法
 *
 * @author chenhonghao
 * @date 2019-09-23 15:03
 */
@Slf4j
public class QuickSortTest {
    public static void main(String[] args) {
        int[] arr = new int[]{45,1233,5,34,40,2,12,232};
        int min ;
        int tempValue;
        for (int i = 0; i < arr.length-1; i++) {
            min = i;
            for (int j = i+1; j < arr.length; j++) {
                if (arr[j] < arr[min]){
                    min = j;
                }
            }
            if (min != i){
                tempValue = arr[i];
                arr[i] =  arr[min];
                arr[min] = tempValue;
            }
            log.info("第{}次排序之后的数组为{}",i,arr);
        }
        log.info("{}",arr);
    }
}
