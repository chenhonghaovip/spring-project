package com.honghao.cloud.leetcode.medium;

import org.junit.Test;

import java.util.*;

/**
 * 括号生成
 *
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 * @author chenhonghao
 * @date 2020-07-08 10:17
 */
public class BracketGenerationTest {
    @Test
    public void test(){
        List<String> list = generateParenthesis(3);
        System.out.println(list);
    }

    public List<String> generateParenthesis(int n) {
        ArrayList<String> strings = new ArrayList<>();
        // 字符串长度
        List<String> list = list(Arrays.asList("(", ")"), n, 2);
        // 判断list的有效性
        for (String s : list) {
            if (isValid(s)){
                strings.add(s);
            }
        }

        return strings;
    }

    private List<String> list(List<String> list,int n ,int index){
        if (index<=2*n){
            ArrayList<String> arrayList = new ArrayList<>();
            for (String s : list) {
                arrayList.add(s+"(");
                arrayList.add(s+")");
            }
            return list(arrayList,n,++index);
        }
        return list;
    }

    public boolean isValid(String s) {
        Map<String,String> map = new HashMap<>();
        map.put(")", "(");
        map.put("}", "{");
        map.put("]", "[");


        Stack<String> stack = new Stack<>();

        char[] chars = s.toCharArray();

        for (char aChar : chars) {
            String value = String.valueOf(aChar);
            if (map.containsValue(value)){
                stack.push(value);
            }else {
                if (stack.isEmpty()) {
                    return false;
                }
                String pop = stack.pop();
                if (!map.get(value).equals(pop)){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
