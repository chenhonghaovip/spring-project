package com.honghao.cloud.leetcode.algorithm.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * 折半查找算法
 *
 * @author chenhonghao
 * @date 2019-09-19 15:02
 */
@Slf4j
public class HalfFoldTest {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 4, 6, 7, 21, 34, 45, 56, 67, 87, 98};
        int high = arr.length - 1, low = 0;
        int middle;
        int k = 21;
        while (low <= high) {
            middle = (low + high) / 2;
            if (arr[middle] > k) {
                high = middle - 1;
            } else if (arr[middle] < k) {
                low = middle + 1;
            } else {
                log.info("位置为{}", middle);
                return;
            }
        }

    }
}
