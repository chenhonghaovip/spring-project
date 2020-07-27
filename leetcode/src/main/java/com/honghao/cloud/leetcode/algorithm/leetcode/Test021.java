package com.honghao.cloud.leetcode.algorithm.leetcode;

import com.honghao.cloud.leetcode.algorithm.sort.ListNode;

/**
 * K 个一组翻转链表
 *
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 *
 * k 是一个正整数，它的值小于或等于链表的长度。
 *
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * @author chenhonghao
 * @date 2019-10-15 10:27
 */
public class Test021 {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode root = new ListNode(0);
        root.next = head;

        ListNode pre = root;
        ListNode end = root;
        while (end.next!=null){
            for (int i = 0; i < k && end!=null ; i++) {
                end = end.next;
            }
            if (end == null){
                break;
            }
            ListNode begin = pre.next;
            //下一组翻转节点的首节点
            ListNode next = end.next;
            end.next = null;
            //开始对节点进行翻转
            pre.next = reverse(head);

            begin.next = next;
            pre = begin;
            end = pre;
        }
        return root.next;
    }

    public ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
}
