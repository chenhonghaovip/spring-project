package com.honghao.cloud.userapi.leetcode;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenhonghao
 * @date 2020-06-05 10:02
 */
public class Test {

    @org.junit.Test
    public void test(){
        System.out.println();
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre =  new ListNode(0);
        ListNode cur = pre;
        int first;
        int carry = 0;

        while (l1!=null || l2!=null){
            int l1Val = l1!=null?l1.val:0;
            int l2Val = l2!=null?l2.val:0;

            first = (l1Val+l2Val + carry)%10;
            carry = (l1Val+l2Val + carry)/10;

            cur.next = new ListNode(first);
            cur = cur.next;

            l1 = l1!=null?l1.next:l1;
            l2 = l2!=null?l2.next:l2;
        }

        if (carry == 1){
            cur.next = new ListNode(carry);
        }
        return pre.next;
    }

    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }


    @Data
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode swapPairs(ListNode head) {
        ListNode cur = head;
        while (cur!=null && cur.next!=null){
            ListNode second = cur.next;
            cur.next = second.next;
            second.next = cur;
            cur = cur.next;
        }
        return head;
    }

    @org.junit.Test
    public void test11(){
        System.out.println(lengthOfLongestSubstring1("abcabcbb"));
    }
    public int lengthOfLongestSubstring1(String s) {
        int max = 0;
        int length = s.length();
        Map<Character,Integer> map = new HashMap<>();
        for (int start = 0,end = 0; end < length; end++) {
            char endChar = s.charAt(end);
            if (map.containsKey(endChar) && map.get(endChar)>=start){
                start = map.get(endChar)+1;
            }

            max = Math.max(max,end-start+1);
            map.put(endChar,end);
        }
        return max;
    }

}
