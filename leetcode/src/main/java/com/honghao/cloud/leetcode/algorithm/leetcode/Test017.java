package com.honghao.cloud.leetcode.algorithm.leetcode;

import com.honghao.cloud.leetcode.algorithm.sort.ListNode;

/**
 * 合并K个排序链表
 *
 * @author chenhonghao
 * @date 2019-10-15 09:16
 */
public class Test017 {
    public static void main(String[] args) {

    }
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0){
            return null;
        }
        ListNode first = lists[0];

        for (int i = 1; i < lists.length; i++) {
            first = getNodes(first,lists[i]);
        }
        return first;
    }

    public ListNode getNodes(ListNode l1,ListNode l2){
        ListNode root = new ListNode(0);
        ListNode p = root;
        while(l1!=null && l2!=null){
            ListNode node;
            if(l1.val>=l2.val){
                node = new ListNode(l2.val);
                l2 = l2.next;
            }else{
                node = new ListNode(l1.val);
                l1 = l1.next;
            }
            p.next = node;
            p = p.next;
        }
        while(l1!=null){
            ListNode node = new ListNode(l1.val);
            l1 = l1.next;
            p.next = node;
            p = p.next;
        }
        while(l2!=null){
            ListNode node = new ListNode(l2.val);
            l2 = l2.next;
            p.next = node;
            p = p.next;
        }
        return root.next;
    }
}
