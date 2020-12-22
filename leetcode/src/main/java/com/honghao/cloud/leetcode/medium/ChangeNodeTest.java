package com.honghao.cloud.leetcode.medium;

import com.honghao.cloud.leetcode.common.ListNode;
import org.junit.Test;

/**
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 *
 * @author chenhonghao
 * @date 2020-07-08 14:15
 */
public class ChangeNodeTest {
    @Test
    public void test() {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        l1.next.next.next = new ListNode(4);
        ListNode listNode = swapPairs(l1);
        System.out.println(listNode);
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }
}
