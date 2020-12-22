package com.honghao.cloud.leetcode.medium;

import org.junit.Test;

import java.util.*;

/**
 * 三个数之和
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 *
 * @author chenhonghao
 * @date 2020-07-07 15:26
 */
public class SanzanoKazu {
    @Test
    public void test() {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};

        System.out.println(threeSum(nums));
    }

    private List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        Set<List<Integer>> sets = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                return new ArrayList<>(sets);
            }
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum < 0) {
                    j++;
                } else if (sum > 0) {
                    k--;
                } else {
                    sets.add(new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[k])));
                    j++;
                }
            }
        }
        return new ArrayList<>(sets);
    }
}
