package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 整数反转
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *
 * @author chenhonghao
 * @date 2019-10-12 13:18
 */
@Slf4j
public class Test007 {
    public static void main(String[] args) {
        reverse(-1230);
    }

    private static int reverse(int x) {
        int sum = 0;
        int num = 0;
        while (x != 0) {
            num = x % 10;
            x = x / 10;
            if (sum > Integer.MAX_VALUE / 10 || sum < Integer.MIN_VALUE / 10) {
                return 0;
            }
            sum = 10 * sum + num;
        }
        return sum;
    }
}
