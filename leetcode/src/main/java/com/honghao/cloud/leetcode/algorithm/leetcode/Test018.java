package com.honghao.cloud.leetcode.algorithm.leetcode;

import com.honghao.cloud.leetcode.algorithm.sort.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 合并K个排序链表
 * 分治算法
 *
 * @author chenhonghao
 * @date 2019-10-15 09:16
 */
public class Test018 {
    public static void main(String[] args) {

    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        List<ListNode> listNodes = Arrays.asList(lists);
        return digui(listNodes);
    }

    public ListNode digui(List<ListNode> lists) {
        List<ListNode> listNodes = new ArrayList<>();
        int lenght = (lists.size() + 1) / 2;
        for (int i = 0; i < lenght; i++) {
            listNodes.add(getNodes(lists.get(2 * i), 2 * i + 1 >= lists.size() ? null : lists.get(2 * i + 1)));
        }
        if (lists.size() == 1) {
            return lists.get(0);
        } else {
            return digui(listNodes);
        }
    }

    public ListNode getNodes(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode p = root;
        while (l1 != null && l2 != null) {
            ListNode node;
            if (l1.val >= l2.val) {
                node = new ListNode(l2.val);
                l2 = l2.next;
            } else {
                node = new ListNode(l1.val);
                l1 = l1.next;
            }
            p.next = node;
            p = p.next;
        }
        while (l1 != null) {
            ListNode node = new ListNode(l1.val);
            l1 = l1.next;
            p.next = node;
            p = p.next;
        }
        while (l2 != null) {
            ListNode node = new ListNode(l2.val);
            l2 = l2.next;
            p.next = node;
            p = p.next;
        }
        return root.next;
    }
}
