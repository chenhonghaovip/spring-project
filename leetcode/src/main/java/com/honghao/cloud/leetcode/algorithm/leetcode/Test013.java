package com.honghao.cloud.leetcode.algorithm.leetcode;

/**
 * 最长公共前缀
 *
 * @author chenhonghao
 * @date 2019-10-14 13:32
 */
public class Test013 {
    public static void main(String[] args) {
        String[] strs = new String[]{"aa","a"};
        System.out.println(longestCommonPrefix(strs));
        longestCommonPrefix(strs);
    }

    private static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }
}
