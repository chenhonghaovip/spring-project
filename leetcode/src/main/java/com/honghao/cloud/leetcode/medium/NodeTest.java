package com.honghao.cloud.leetcode.medium;

import org.junit.Test;

/**
 * 删除链表的倒数第N个节点
 *
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * @author chenhonghao
 * @date 2020-07-07 17:40
 */
public class NodeTest {
    @Test
    public void test(){

        ListNode five = new ListNode(5);
        ListNode four = new ListNode(4);
        ListNode three = new ListNode(3);
        ListNode second = new ListNode(2);
        ListNode first = new ListNode(1);
        first.next = second;
        second.next = three;
        three.next = four;
        four.next = five;
        ListNode listNode = removeNthFromEnd(first, 5);
        System.out.println(listNode);
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode start = pre;
        ListNode end = pre;
        for (int i = 0; i < n; i++) {
            end = end.next;
        }
        while (end.next!=null){
            end = end.next;
            start = start.next;
        }
        start.next = start.next.next;
        return pre.next;
    }








    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

}
