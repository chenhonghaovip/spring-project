package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 删除排序数组中的重复项
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 *
 * @author chenhonghao
 * @date 2019-10-15 15:58
 */
@Slf4j
public class Test020 {
    public static void main(String[] args) {
        Test020 test020 = new Test020();
        int[] nums = new int[]{1, 1, 2};
        int m = test020.removeDuplicates(nums);
        System.out.println(m);
    }

    public int removeDuplicates(int[] nums) {
        int k = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != nums[i + 1]) {
                nums[k] = nums[i + 1];
                k++;

            }
        }
        return k;
    }
}
