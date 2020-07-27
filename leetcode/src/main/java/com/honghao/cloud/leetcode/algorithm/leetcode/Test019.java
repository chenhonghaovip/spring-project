package com.honghao.cloud.leetcode.algorithm.leetcode;

import com.honghao.cloud.leetcode.algorithm.sort.ListNode;

/**
 * 两两交换链表中的节点
 *
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 * @author chenhonghao
 * @date 2019-10-15 10:01
 */
public class Test019 {
    public static void main(String[] args) {

    }

    public ListNode swapPairs(ListNode head) {
        if (head==null || head.next==null){
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }
}
