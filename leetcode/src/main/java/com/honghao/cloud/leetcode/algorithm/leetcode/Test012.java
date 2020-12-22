package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 罗马数字转整数
 *
 * @author chenhonghao
 * @date 2019-10-12 17:09
 */
@Slf4j
public class Test012 {
    public static void main(String[] args) {

        String s = "LVIII";
        romanToInt(s);
    }

    private static int romanToInt(String s) {
        int[] arr = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        String[] strs = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        Map<String, Integer> map = new HashMap<>(strs.length * 2);
        for (int i = 0; i < arr.length; i++) {
            map.put(strs[i], arr[i]);
        }
        char[] chars = s.toCharArray();
        int i = 0;
        int sum = 0;
        System.out.println(getNum(map, sum, chars, i));
        return getNum(map, sum, chars, i);
    }

    private static int getNum(Map<String, Integer> map, int sum, char[] chars, int i) {
        if (i + 1 <= chars.length - 1 && map.get(String.valueOf(chars[i])) < map.get(String.valueOf(chars[i + 1]))) {
            sum = sum + map.get(chars[i] + String.valueOf(chars[i + 1]));
            i = i + 2;
        } else if (i <= chars.length - 1) {
            sum += map.get(String.valueOf(chars[i]));
            i++;
        } else {
            return sum;
        }
        return getNum(map, sum, chars, i);
    }
}
