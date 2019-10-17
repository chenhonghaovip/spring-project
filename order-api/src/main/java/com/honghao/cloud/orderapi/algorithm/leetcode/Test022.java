package com.honghao.cloud.orderapi.algorithm.leetcode;

/**
 * 移除元素
 *给定一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，返回移除后数组的新长度。
 *
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 *
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 * @author chenhonghao
 * @date 2019-10-15 16:14
 */
public class Test022 {
    public static void main(String[] args) {
        Test022 test020 = new Test022();
        int[] nums = new int[]{3,2,2,3};
        int m = test020.removeElement(nums,2);
        System.out.println(m);
    }

    public int removeElement(int[] nums, int val) {
        int k = 0; int m = 0;
        for (int i = 0; i < nums.length; i++) {
            if (val!=nums[i]){
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }
}
