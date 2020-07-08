package com.honghao.cloud.leetcode.medium;

import org.junit.Test;

import java.util.Arrays;

/**
 * 最接近的三数之和
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 *
 * @author chenhonghao
 * @date 2020-07-07 15:26
 */
public class NearSanzanoKazu {
    @Test
    public void test(){
        int[] nums = new int[]{-1,2,1,-4};
        int target = 1;
        System.out.println(threeSumClosest(nums,target));
    }

    private int threeSumClosest(int[] nums, int target) {
        int value = 0;
        int abs = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {

            int j = i+1;
            int k = nums.length-1;

            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                int abs1 = Math.abs(sum - target);

                if (sum < target){
                    j++;
                    if (abs1 <abs){
                        abs = abs1;
                        value = sum;
                    }
                }else if (sum > target){
                    k--;
                    if (abs1 <abs){
                        abs = abs1;
                        value = sum;
                    }
                }else {
                    return target;
                }
            }
        }
        return value;
    }
}
