package com.honghao.cloud.leetcode.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 有效的括号
 *
 * @author chenhonghao
 * @date 2019-10-14 16:02
 */
public class Test015 {
    public static void main(String[] args) {
        String s = "(]";
        System.out.println(isValid(s));

    }
    private static boolean isValid(String s) {
        Map mappings = new HashMap<String, String>(8);
        mappings.put(")", "(");
        mappings.put("}", "{");
        mappings.put("]", "[");

        Stack<String> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (mappings.containsValue(String.valueOf(aChar))){
                stack.push(String.valueOf(aChar));
            }else {
                if (stack.empty()){return false;}
                if (mappings.get(String.valueOf(aChar)).equals(stack.peek())){
                    stack.pop();
                }else {
                    return false;
                }
            }
        }
        return stack.empty();
    }
}
