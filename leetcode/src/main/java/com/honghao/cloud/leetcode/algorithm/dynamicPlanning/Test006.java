package com.honghao.cloud.leetcode.algorithm.dynamicPlanning;

/**
 * 返回两个字符串的最长公共子序列
 * 动态规划算法
 *
 * @author chenhonghao
 * @date 2019-10-17 13:59
 */
public class Test006 {
    public static void main(String[] args) {
        Test006 test006 = new Test006();
        String haystack = "helabclo", needle = "abcdabd";
        System.out.println(test006.getMax(haystack, needle));
    }

    public int getMax(String haystack, String needle) {
        char[] p = haystack.toCharArray();
        char[] n = needle.toCharArray();
        int[][] temp = new int[haystack.length() + 1][needle.length() + 1];

        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < n.length; j++) {
                if (p[i] == n[j]) {
                    temp[i + 1][j + 1] = temp[i][j] + 1;
                } else {
                    temp[i + 1][j + 1] = Math.max(temp[i][j + 1], temp[i + 1][j]);
                }
            }
        }

        return temp[p.length][n.length];
    }

    ;
}
