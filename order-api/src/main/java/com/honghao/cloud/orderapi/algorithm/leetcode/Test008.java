package com.honghao.cloud.orderapi.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串转换整数 (atoi)
 *
 * @author chenhonghao
 * @date 2019-10-12 13:43
 */
@Slf4j
public class Test008 {
    public static void main(String[] args) {
        myAtoi("");
        log.info("{}",myAtoi(""));
    }
    private static int myAtoi(String str) {
        if (str == null || str == ""){
            return 0;
        }
        char[] chars = str.trim().toCharArray();
        int carry = 1;
        int sum = 0;

        char[] chars1 = str.trim().substring(1).toCharArray();
        if (chars[0]=='-'){
            carry = -1;
            chars = chars1;
        }else if (chars[0]=='+'){
            carry = 1;
            chars = chars1;
        }
        for (char aChar : chars) {
            if (aChar - 48 > 9 || aChar - 48 < 0){
                break;
            }
            if (sum > Integer.MAX_VALUE/10){
                if (carry == 1){
                    return Integer.MAX_VALUE;
                }
                if (carry == -1){
                    return Integer.MIN_VALUE;
                }

                break;
            }
            sum = sum * 10 + aChar - 48;
        }
        return sum*carry;
    }
}
