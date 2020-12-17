package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * 无重复字符的最长子串
 *
 * @author chenhonghao
 * @date 2019-10-11 14:27
 */
@Slf4j
public class Test001 {
    public static void main(String[] args) {
        String s = "abcabcbb";
        Test001 solution = new Test001();
        System.out.println(solution.getMax(s));
    }

    private int getMax(String s) {
        char[] arr = s.toCharArray();
        int max = 0;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (!set.contains(arr[j])) {
                    set.add(arr[j]);
                    max = j - i + 1 > max ? j - i + 1 : max;
                } else {
                    set.clear();
                    break;
                }
            }
        }
        return max;
    }
}
