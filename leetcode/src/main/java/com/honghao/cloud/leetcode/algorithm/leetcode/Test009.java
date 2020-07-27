package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 回文数
 *判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * @author chenhonghao
 * @date 2019-10-12 15:36
 */
@Slf4j
public class Test009 {
    public static void main(String[] args) {
        isPalindrome(121);
        System.out.println(isPalindrome(121));
    }
    private static boolean isPalindrome(int x) {
        if (x < 0){
            return false;
        }
        String s = String.valueOf(x);
        char[] chars = s.toCharArray();
        boolean flag = true;
        for (int i = 0; i < chars.length / 2; i++) {
            if (chars[i] != chars[chars.length-1-i]){
                flag = false;
            }
        }
        return flag;
    }
}
