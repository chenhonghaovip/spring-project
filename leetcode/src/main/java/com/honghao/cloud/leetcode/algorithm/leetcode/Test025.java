package com.honghao.cloud.leetcode.algorithm.leetcode;

/**
 * 两数相除
 * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
 *
 * @author chenhonghao
 * @date 2019-10-16 11:16
 */
public class Test025 {
    public static void main(String[] args) {
        Test025 test025 = new Test025();
        System.out.println(test025.divide(-11, -1));
    }

    public int divide(int dividend, int divisor) {
        if (dividend == 0 || divisor == 0) {
            return 0;
        }
        int k = 0;
        if (dividend > 0 && divisor > 0) {
            while (dividend - divisor >= 0) {
                dividend = dividend - divisor;
                k++;
            }
            return k;
        } else if (dividend < 0 && divisor < 0) {
            while (dividend - divisor <= 0) {
                dividend = dividend - divisor;
                k++;
            }
            return k;
        } else if (dividend < 0 && divisor > 0) {
            while (dividend + divisor <= 0) {
                dividend = dividend + divisor;
                k--;
            }
            return k;
        } else {
            while (dividend + divisor >= 0) {
                dividend = dividend + divisor;
                k--;
            }
            return k;
        }
    }
}
