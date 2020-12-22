package com.honghao.cloud.userapi.test.offer;

import org.junit.Test;

import java.util.Stack;

/**
 * @author chenhonghao
 * @date 2020-09-28 13:25
 */
public class ArraysTest {

    /**
     * 题目：在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     * 1 2 8 9
     * 2 4 9 12
     * 4 7 10 13
     * 6 8 11 15
     * 解决思路：
     * 每次取右上角的数字和当前数字做对比，如果大于当前数字，则该列都大于当前数字，过滤
     * 如果小于当前数字，则该行都小于当前数字，也过滤，不断循环查询
     */
    @Test
    public void test() {
        int[][] arr = {{1, 2, 4, 6}, {2, 4, 7, 8}, {8, 9, 10, 11}, {9, 12, 13, 15}};
        int k = 7;
        int h = 0;
        int w = arr.length - 1;
        while (w >= 0 && h < arr[0].length) {
            if (arr[w][h] == k) {
                System.out.println("w = " + w + " h = " + h);
                return;
            } else if (arr[w][h] > k) {
                w = w - 1;
            } else {
                h = h + 1;
            }
        }
        System.out.println("error");
    }

    /**
     * 题目：输入一个链表的头结点，从尾到头反过来打印出每个结点的值。
     * 解决思路1：利用栈功能实现
     * 解决思路2：利用递归实现
     */
    @Test
    public void test1() {
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        listNode.next = listNode2;
        listNode2.next = listNode3;

        Stack<Integer> stack = new Stack<>();
        ListNode p = listNode;
        while (p != null) {
            stack.push(p.value);
            p = p.next;
        }
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
        digui(listNode);
    }

    private void digui(ListNode listNode) {
        if (listNode != null) {
            digui(listNode.next);
            System.out.print(listNode.value);
        }
    }

    /**
     * 题目：输入一个链表，输出该链表中倒数第 k 个结点。为了符合大多数人的习惯，本题从1 开始计数，即链表的尾结点是倒数第1 个结点。
     * 例如一个链表有6个结点，从头结点开始它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个结点是值为4的结点。
     * 解决思路：双指针，长度为k,尾指针指向最后一个节点时，头指针正好指向倒数第k个节点
     */
    @Test
    public void test2() {

        ListNode listNode = createList();
        int k = 2;
        ListNode first = listNode;
        ListNode second = listNode;
        for (int i = 0; i < k - 1; i++) {
            second = second.next;
        }
        while (second.next != null) {
            second = second.next;
            first = first.next;
        }
        System.out.println(first.value);

    }

    private ListNode createList() {
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);
        listNode.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        return listNode;
    }


    /**
     * 输入一个链表的头结点，反转该链表并输出反转后链表的头结点 1-2-3-4-5-6
     * 翻转后为6-5-4-3-2-1
     */
    @Test
    public void test3() {
        ListNode head = createList();
        reverseList(head);


    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev, cur, end;
        prev = null;
        cur = head;
        end = head.next;
        while (cur != null) {
            cur.next = prev;
            prev = cur;
            cur = end;
            if (end != null) {
                end = end.next;
            }
        }
        return prev;

    }

    /**
     * 删除链表的倒数第N个节点
     * 1-2-3-4-5-6
     */
    @Test
    public void test4() {
        int k = 3;
        ListNode listNode = createList();
        ListNode head = listNode;
        ListNode tail = listNode;
        ListNode end = listNode;
        for (int i = 0; i < k; i++) {
            end = end.next;
        }
        while (end.next != null) {
            end = end.next;
            tail = tail.next;
        }
        tail.next = tail.next.next;
        System.out.println(head.value);
    }

    /**
     * 两两交换链表中的节点
     */
    @Test
    public void test5() {
        ListNode list = createList();
        swapPairs(list);
    }

    private ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;

        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode pre = new ListNode(0);
        ListNode tail = pre;
        ListNode end = pre;

        for (int i = 0; i < k; i++) {
            if (end.next == null) {
                break;
            }
            end = end.next;
        }


        head.next = reverseKGroup(end.next, k);
        return head;
    }


    @Test
    public void test6() {
        int[] a = {1, 3, 5, 6};
        int tag = 0;
        System.out.println(searchInsert(a, tag));
    }

    public int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }


    public static class ListNode {
        private int value;
        private ListNode next;

        public ListNode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }
}
