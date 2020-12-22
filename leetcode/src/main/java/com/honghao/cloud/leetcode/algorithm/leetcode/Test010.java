package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 双指针法(或者可以使用暴力破解法)
 * 盛最多水的容器
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * @author chenhonghao
 * @date 2019-10-12 15:48
 */
@Slf4j
public class Test010 {
    public static void main(String[] args) {
        int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        maxArea(height);
        System.out.println(maxArea(height));
    }

    private static int maxArea(int[] height) {
        int start = 0, end = height.length - 1;
        int maxValue = 0;
        int startValue, endValue, rowNum;
        while (start < end) {
            startValue = height[start];
            endValue = height[end];
            rowNum = end - start;
            if (startValue < endValue) {
                maxValue = maxValue > startValue * rowNum ? maxValue : startValue * rowNum;
                start++;
            } else {
                maxValue = maxValue > endValue * rowNum ? maxValue : endValue * rowNum;
                end--;
            }
        }
        return maxValue;
    }
}
