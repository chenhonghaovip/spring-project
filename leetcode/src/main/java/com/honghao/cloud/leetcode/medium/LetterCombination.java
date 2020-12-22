package com.honghao.cloud.leetcode.medium;

import org.junit.Test;

import java.util.*;

/**
 * 四数之和
 * <p>
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
 *
 * @author chenhonghao
 * @date 2020-07-07 16:07
 */
public class LetterCombination {
    @Test
    public void test() {
        int[] nums = new int[]{1, 0, -1, 0, -2, 2};
        int target = 0;
        System.out.println(fourSum(nums, target));
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> sets = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > target) {
                return new ArrayList<>(sets);
            }
            int sum = target - nums[i];

            for (int j = i + 1; j < nums.length; j++) {
                int k = j + 1;
                int l = nums.length - 1;
                while (k < l) {
                    int sumThree = nums[j] + nums[k] + nums[l];
                    if (sumThree > sum) {
                        l--;
                    } else if (sumThree < sum) {
                        k++;
                    } else {
                        sets.add(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));
                        k++;
                    }
                }
            }
        }
        return new ArrayList<>(sets);
    }
}
