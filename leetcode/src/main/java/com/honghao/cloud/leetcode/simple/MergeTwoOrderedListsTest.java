package com.honghao.cloud.leetcode.simple;

import com.honghao.cloud.leetcode.ListNode;
import org.junit.Test;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 *
 * @author chenhonghao
 * @date 2020-07-08 10:07
 */
public class MergeTwoOrderedListsTest {
    @Test
    public void test(){
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);
        ListNode listNode = mergeTwoLists(l1, l2);
        System.out.println(listNode);
    }


    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode first = pre;

        while (l1!=null && l2!=null){
            int value;
            if (l1.val>l2.val){
                value = l2.val;
                l2 = l2.next;
            }else {
                value = l1.val;
                l1 = l1.next;
            }
            pre.next = new ListNode(value);
            pre = pre.next;
        }

        while (l1!=null){
            pre.next = l1;
            l1 = null;
        }

        while (l2!=null){
            pre.next = l2;
            l2 = null;
        }
        return first.next;
    }
}
