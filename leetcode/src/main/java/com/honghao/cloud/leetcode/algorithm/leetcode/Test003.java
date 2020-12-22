package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 寻找两个有序数组的中位数
 *
 * @author chenhonghao
 * @date 2019-10-11 15:39
 */
@Slf4j
public class Test003 {
    public static void main(String[] args) {
        int[] nums1 = {1, 3, 5, 7, 9, 12, 14, 15, 56, 71};
        int[] nums2 = {2, 4, 6, 8, 10};
        int[] result = new int[nums1.length + nums2.length];
        int indexA = 0, indexB = 0;
        int i;
        for (i = 0; i < result.length; i++) {
            if (indexA == nums1.length || indexB == nums2.length) {
                break;
            }
            if (nums1[indexA] < nums2[indexB]) {
                result[i] = nums1[indexA++];
            } else {
                result[i] = nums2[indexB++];
            }
        }
        while (indexA < nums1.length) {
            result[i++] = nums1[indexA++];
        }
        while (indexB < nums2.length) {
            result[i++] = nums2[indexB++];
        }
        double num;
        if (result.length % 2 == 0) {
            num = (result[result.length / 2] + result[result.length / 2 - 1]) / 2.0;
        } else {
            num = result[result.length / 2];
        }
        System.out.println(num);
        log.info("{}", result);
    }
}
