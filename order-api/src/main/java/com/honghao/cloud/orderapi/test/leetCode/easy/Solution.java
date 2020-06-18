package com.honghao.cloud.orderapi.test.leetCode.easy;

/**
 * @author chenhonghao
 * @date 2020-06-18 11:14
 */
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
         System.out.println(solution.climbStairs(4));
    }

    public int climbStairs(int n) {
        int p = 0,q = 0,r = 1;
        for (int i = 1; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }
}
