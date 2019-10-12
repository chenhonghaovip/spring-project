package com.honghao.cloud.orderapi.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 无重复字符的最长子串优化
 * @author chenhonghao
 * @date 2019-10-11 15:09
 */
@Slf4j
public class Test002 {
    public static void main(String[] args) {
        String s = " ";
        List<Character> list = new ArrayList();
        char[] arr = s.toCharArray();
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            while (list.contains(arr[i])){
                list.remove(0);
            }
            if (!list.contains(arr[i])){
                list.add(arr[i]);
            }
            max = list.size()> max?list.size():max;
        }
        System.out.println(max);
    }
}
