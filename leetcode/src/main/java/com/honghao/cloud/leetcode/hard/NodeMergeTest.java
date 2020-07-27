package com.honghao.cloud.leetcode.hard;

import com.honghao.cloud.leetcode.common.ListNode;
import org.junit.Test;

/**
 * @author chenhonghao
 * @date 2020-07-08 13:24
 */
public class NodeMergeTest {
    @Test
    public void test(){
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(5);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);

        ListNode l3 = new ListNode(2);
        l3.next = new ListNode(6);
        ListNode[] listNodes = new ListNode[]{l1,l2,l3};

        ListNode node = mergeKLists(listNodes);
        System.out.println(node);
    }
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode root = new ListNode(0);
        ListNode first = root;

        while (true){
            ListNode minNode = null;
            int minPointer = -1;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] == null){
                    continue;
                }
                if (minNode == null || minNode.val > lists[i].val){
                    minNode = lists[i];
                    minPointer = i;
                }
            }
            if (minPointer == -1){
                break;
            }

            first.next = minNode;
            first = first.next;
            lists[minPointer] = lists[minPointer].next;
        }

        return root.next;
    }
//    public ListNode mergeKLists(ListNode[] lists) {
//        ListNode temp = null;
//        for (ListNode list : lists) {
//            temp = mergeTwoLists(temp,list);
//        }
//        return temp;
//    }
//    public ListNode mergeKLists(ListNode[] lists) {
//        int k = lists.length;
//        ListNode dummyHead = new ListNode(0);
//        ListNode tail = dummyHead;
//        while (true) {
//            ListNode minNode = null;
//            int minPointer = -1;
//            for (int i = 0; i < k; i++) {
//                if (lists[i] == null) {
//                    continue;
//                }
//                if (minNode == null || lists[i].val < minNode.val) {
//                    minNode = lists[i];
//                    minPointer = i;
//                }
//            }
//            if (minPointer == -1) {
//                break;
//            }
//            tail.next = minNode;
//            tail = tail.next;
//            lists[minPointer] = lists[minPointer].next;
//        }
//        return dummyHead.next;
//    }



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
