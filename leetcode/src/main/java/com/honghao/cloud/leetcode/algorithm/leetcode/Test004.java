package com.honghao.cloud.leetcode.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 最长回文子串(中心拓展方法)
 *
 * @author chenhonghao
 * @date 2019-10-12 09:03
 */
@Slf4j
public class Test004 {
    public static void main(String[] args) {
      String s = "abccbeddeb";
      Test004 test004 = new Test004();
      test004.longestPalindrome(s);

    }

    public String longestPalindrome(String s) {
        if(StringUtils.isBlank(s)){
            return StringUtils.EMPTY;
        }
        int start = 0,end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = maxLength(s,i,i);
            int len2 = maxLength(s,i,i+1);
            int max = Math.max(len1,len2);
            if (max > end - start){
                start = i - (max-1)/2;
                end = i + max/2;
            }
        }
        System.out.println(s.substring(start,end+1));
        return s.substring(start,end+1);
    }

    public int maxLength(String s ,int left , int right){
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return right-left-1;
    }
}
