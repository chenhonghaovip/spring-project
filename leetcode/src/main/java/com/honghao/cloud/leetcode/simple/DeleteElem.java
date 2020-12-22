package com.honghao.cloud.leetcode.simple;

import org.junit.Test;

/**
 * @author chenhonghao
 * @date 2020-07-08 16:15
 */
public class DeleteElem {
    /**
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     */
    @Test
    public void test() {
        int val = 3;
        int[] nums = new int[]{3, 2, 2, 3};
        System.out.println(removeElement(nums, val));
    }

    public int removeElement(int[] nums, int val) {
        int lenght = 0;
        for (int num : nums) {
            if (num != val) {
                nums[lenght] = num;
                lenght++;
            }
        }
        return lenght;
    }


    /**
     * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
     */

    @Test
    public void test1() {
        String haystack = "hello", needle = "ll";
        System.out.println(strStr(haystack, needle));
    }


    public int strStr(String haystack, String needle) {
        int l = needle.length(), n = haystack.length();

        for (int start = 0; start < n - l + 1; ++start) {
            if (haystack.substring(start, start + l).equals(needle)) {
                return start;
            }
        }
        return -1;
    }

    @Test
    public void test2() {
        int dividend = 7, divisor = -3;
        System.out.println(divide(dividend, divisor));
    }

    public int divide(int dividend, int divisor) {
        int basic = 1;
        if (dividend > 0 && divisor < 0 || dividend < 0 && divisor > 0) {
            basic = -1;
        }
        int mod;
        int value = 0;

        while ((mod = Math.abs(dividend) - Math.abs(divisor)) >= 0) {
            dividend = mod;
            value++;
        }
        if (basic > 0) {
            return value;
        } else {
            return 0 - value;
        }
    }

    @Test
    public void test3() {
        int[] height = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(trap(height));
    }

    public int trap(int[] height) {
        int maxValue = 0;
        int maxIndex = -1;
        for (int i = 0; i < height.length; i++) {
            if (maxValue <= height[i]) {
                maxValue = height[i];
                maxIndex = i;
            }
        }

        int sum = 0;
        for (int i = 1; i < height.length - 1; i++) {
            if (i < maxIndex && height[i] < height[i - 1]) {
                sum += height[i - 1] - height[i];
            }
            if (i > maxIndex && height[i + 1] > height[i]) {
                sum += height[i + 1] - height[i];
            }
        }

        return sum;
    }


}
