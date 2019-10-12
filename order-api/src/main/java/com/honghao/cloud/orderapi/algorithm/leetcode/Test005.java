package com.honghao.cloud.orderapi.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * Z 字形变换
 *将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 *
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 *
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 *
 * 时间复杂度：O(m*n)
 * @author chenhonghao
 * @date 2019-10-12 09:52
 */
@Slf4j
public class Test005 {
    public static void main(String[] args) {
        convert("LEETCODEISHIRING",1);
    }
    private static String convert(String s, int numRows) {
        if (numRows == 1){
            return s;
        }
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        int temp = 2*numRows - 2;
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < chars.length; i++) {
                if (i%temp == j || i%temp == (temp-j)){
                    stringBuilder.append(chars[i]);
                }
            }
        }
        return stringBuilder.toString();
    }
}
