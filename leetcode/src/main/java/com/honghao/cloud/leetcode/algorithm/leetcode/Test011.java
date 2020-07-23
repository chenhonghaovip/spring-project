package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 整数转罗马数字
 *字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 *特殊规则：
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 *
 * @author chenhonghao
 * @date 2019-10-12 16:06
 */
@Slf4j
public class Test011 {
    public static void main(String[] args) {
        System.out.println(intToRoman(1994));
    }
    private static String intToRoman(int num) {
        int[] arr = new int[]{1,4,5,9,10,40,50,90,100,400,500,900,1000};
        String[] strs = new String[]{"I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};
        StringBuilder stringBuilder = new StringBuilder();
        getType(num, stringBuilder, arr.length - 1, arr, strs);
        return stringBuilder.toString();
    }

    private static StringBuilder getType(int num, StringBuilder stringBuilder, int key ,int[] arr,String[] strings){
        int temp = arr[key];
        int M = num/temp;
        num = num%temp;
        for (int i = 0; i < M; i++) {
            stringBuilder.append(strings[key]);
        }
        if (--key>=0){
            getType(num,stringBuilder,key,arr,strings);
        }
        return stringBuilder;
    }
}
