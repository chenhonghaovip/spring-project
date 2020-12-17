package com.honghao.cloud.leetcode.simple;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 有效的括号
 *
 * @author chenhonghao
 * @date 2020-07-08 09:51
 */
public class ValidParenthesisTest {
    @Test
    public void test() {
        String nums = "{[]}";

        System.out.println(isValid(nums));
    }

    public boolean isValid(String s) {
        Map<String, String> map = new HashMap<>();
        map.put(")", "(");
        map.put("}", "{");
        map.put("]", "[");


        Stack<String> stack = new Stack<>();

        char[] chars = s.toCharArray();

        for (char aChar : chars) {
            String value = String.valueOf(aChar);
            if (map.containsValue(value)) {
                stack.push(value);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                String pop = stack.pop();
                if (!map.get(value).equals(pop)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
