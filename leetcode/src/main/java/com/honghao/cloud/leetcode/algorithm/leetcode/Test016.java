package com.honghao.cloud.leetcode.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 括号生成
 * <p>
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 *
 * @author chenhonghao
 * @date 2019-10-14 17:08
 */
public class Test016 {
    public static void main(String[] args) {
        List<String> ans = new ArrayList();
        backtrack(ans, "", 0, 0, 3);
        System.out.println(ans);
    }

    public static void backtrack(List<String> ans, String cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }
        if (open < max) {
            backtrack(ans, cur + "(", open + 1, close, max);
        }
        if (close < open) {
            backtrack(ans, cur + ")", open, close + 1, max);
        }
    }
}
