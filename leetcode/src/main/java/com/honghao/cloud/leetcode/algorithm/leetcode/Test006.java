package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Z 字形变换
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * <p>
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 时间复杂度：O(n)，其中 n ==len(s)
 * 空间复杂度：O(n)
 *
 * @author chenhonghao
 * @date 2019-10-12 11:09
 */
@Slf4j
public class Test006 {
    public static void main(String[] args) {
        convert("LEETCODEISHIRING", 3);
    }

    private static String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(s.length(), numRows); i++) {
            rows.add(new StringBuilder());
        }

        int num = 0;
        boolean goingDown = false;
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            rows.get(num).append(aChar);
            if (num == 0 || num == numRows - 1) {
                goingDown = !goingDown;
            }
            num += goingDown ? 1 : -1;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (StringBuilder row : rows) {
            stringBuilder.append(row);
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
