package com.honghao.cloud.orderapi.algorithm.dynamicPlanning;

/**
 * 两个字符串最大公共子序列
 *
 * @author chenhonghao
 * @date 2019-10-16 09:04
 */
public class Test002 {
    public static void main(String[] args) {
        String haystack = "hello", needle = "abcdabd";
        Test002 test002 = new Test002();
        test002.strStr(haystack,needle);
    }
    public int strStr(String haystack, String needle) {
        char[] t = haystack.toCharArray();
        char[] p = needle.toCharArray();
        //主串的位置
        int i = 0;
        //模式串的位置
        int j = 0;
        int[] next = getNext(needle);
        return 0;
    }

    public static int[] getNext(String ps) {
        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        return next;
    }
}
