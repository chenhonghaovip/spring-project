package com.honghao.cloud.leetcode.sort.binary_search;

import com.honghao.cloud.leetcode.common.ListNode;
import org.junit.Test;

import java.util.*;

/**
 * @author chenhonghao
 * @date 2021-03-18 11:18
 */
public class TestC {
    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();

    public int getMin() {
        if (minStack.isEmpty()) {
            throw new RuntimeException("");
        }
        return minStack.peek();
    }

    public int pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("");
        }
        int pop = stack.pop();
        if (minStack.peek() == pop) {
            minStack.pop();
        }
        return pop;
    }

    public void push(int value) {
        stack.push(value);
        if (minStack.isEmpty() || minStack.peek() >= value) {
            minStack.push(value);
        }
    }

    private Stack<String> inputStack = new Stack<>();
    private Stack<String> outputStack = new Stack<>();

    public void add(String value) {
        inputStack.add(value);
        pushToPop();
    }

    private void pushToPop() {
        if (outputStack.isEmpty()) {
            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
    }

    public void poll() {
        pushToPop();
        outputStack.pop();
    }

    public String peek() {
        pushToPop();
        return outputStack.peek();
    }


    @Test
    public void test() {
        Stack<String> f1 = new Stack<>();
        Stack<String> f2 = new Stack<>();
        Stack<String> f3 = new Stack<>();
        f1.push("3");
        f1.push("2");
        f1.push("1");
        int size = f1.size();
        process(size, f1, f2, f3, "f1", "f2", "f3");
    }

    public void process(int num, Stack<String> from, Stack<String> middle, Stack<String> to
            , String startName, String middleName, String endName) {
        if (num == 1) {
            System.out.println("move " + from.peek() + " from " + startName + " to " + middleName);
            middle.push(from.pop());
            System.out.println("move " + middle.peek() + " from " + middleName + " to " + endName);
            to.push(middle.pop());
        } else {
            process(num - 1, from, middle, to, startName, middleName, endName);
            System.out.println("move " + from.peek() + " from f1 to f2");
            middle.push(from.pop());
            int i = 0;
            while (!to.isEmpty()) {
                i++;
                process(1, to, middle, from, endName, middleName, startName);
            }
            System.out.println("move " + middle.peek() + " from f2 to f3");
            to.push(middle.pop());
            for (int j = 0; j < i; j++) {
                process(1, from, middle, to, startName, middleName, endName);
            }

        }
    }


    @Test
    public void test11() {
        int[] ar = new int[]{4, 3, 5, 4, 3, 3, 6, 7};
        int n = 3;
        int[] result = new int[ar.length - n + 1];
        for (int i = 0; i < ar.length - n + 1; i++) {
            int max = ar[i];
            if (ar[i + 1] > max) {
                max = ar[i + 1];
            }
            if (ar[i + 2] > max) {
                max = ar[i + 2];
            }
            result[i] = max;
        }
        System.out.println(Arrays.toString(result));
    }


    @Test
    public void test111() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
//        ListNode l3 = new ListNode(3);
//        ListNode l4 = new ListNode(4);
//        ListNode l5 = new ListNode(5);
        l1.next = l2;
//        l2.next = l3;
//        l3.next = l4;
//        l4.next=l5;
        ListNode listNode = reverseBetween(l1, 1, 2);

    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) {
            return head;
        }
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        ListNode leftNode = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            leftNode = leftNode.next;
        }
        ListNode reverLeft = leftNode.next;


        ListNode reverRight = head;
        for (int i = 0; i < right - 1; i++) {
            reverRight = reverRight.next;
        }
        ListNode rightNode = reverRight.next;

        leftNode.next = null;
        reverRight.next = null;

        reverseList(reverLeft);

        leftNode.next = reverRight;
        reverLeft.next = rightNode;
        return dummyNode.next;
    }

    public ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;

        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    @Test
    public void T1() {
        String[] re = new String[]{"2", "1", "+", "3", "*"};
        System.out.println(evalRPN(re));

    }

    public int evalRPN(String[] tokens) {
        List<String> list = Arrays.asList("+", "-", "*", "/");
        Stack<String> stack = new Stack<>();
        int value = 0;
        for (String token : tokens) {
            if (list.contains(token)) {
                int f1 = Integer.valueOf(stack.pop());
                int f2 = Integer.valueOf(stack.pop());


                if ("+".equals(token)) {
                    value = f2 + f1;
                }
                if ("-".equals(token)) {
                    value = f2 - f1;
                }
                if ("*".equals(token)) {
                    value = f2 * f1;
                }
                if ("/".equals(token)) {
                    value = f2 / f1;
                }
                stack.push(String.valueOf(value));
            } else {
                stack.push(token);
            }
        }
        return Integer.valueOf(stack.pop());
    }

    @Test
    public void T12() {
        String value = "a good   example ";
        System.out.println(reverseWords(value));

    }

    public String reverseWords(String s) {
        Stack<String> stack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (" ".equals(String.valueOf(aChar))) {
                if (stringBuilder.length() != 0) {
                    stack.push(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            } else {
                stringBuilder.append(aChar);
            }
        }


        while (!stack.isEmpty()) {
            stringBuilder.append(" ").append(stack.pop());
        }
        if (stringBuilder.toString().startsWith(" ")) {
            return stringBuilder.toString().substring(1);
        }
        return stringBuilder.toString();
    }

    @Test
    public void T13() {
        int[] arr = new int[]{2, 3, -2, 4};
        System.out.println(maxProduct(arr));
    }

    public int maxProduct(int[] nums) {
        int length = nums.length;
        int[] maxF = new int[length];
        int[] minF = new int[length];
        maxF[0] = nums[0];
        minF[0] = nums[0];
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            maxF[i] = Math.max(nums[i], Math.max(nums[i] * maxF[i - 1], nums[i] * minF[i - 1]));
            minF[i] = Math.min(nums[i], Math.min(nums[i] * maxF[i - 1], nums[i] * minF[i - 1]));
            max = Math.max(max, maxF[i]);
        }
        return max;
    }

    @Test
    public void T14() {
        int[] arr = new int[]{1, 1};
        System.out.println(findMin(arr));
    }

    public int findMin(int[] nums) {
        int length = nums.length;
        int i = 0, j = length - 1;
        if (nums[0] < nums[length - 1]) {
            return nums[0];
        }

        while (i < j) {
            int middle = (i + j) / 2;
            if (nums[middle] > nums[j]) {
                i = middle + 1;
            }
            if (nums[middle] <= nums[j]) {
                j = middle;
            }
        }
        return nums[i];
        // 说明被旋转过了

    }

    @Test
    public void T15() {
        int[] arr = new int[]{1, 1};
        System.out.println(findMin(arr));
    }

    public int temp(int[] arr, int num) {
        int length = arr.length;
        int[] max = new int[length];
        int[] min = new int[length];
        max[0] = arr[0];
        min[0] = arr[0];
        int size = 0;
        if (max[0] - min[0] <= num) {
            size++;
        }
        for (int i = 1; i < length; i++) {
            max[i] = Math.max(max[i - 1], arr[i]);
            min[i] = Math.min(max[i - 1], arr[i]);
            if (max[i] - min[i] <= num) {
                size++;
            }
        }
        return size;
    }

    @Test
    public void T16() {
        int[] arr = new int[]{1, 1};
        System.out.println(findMin(arr));
    }

    public int lengthOfLIS(int[] nums) {
        int length = nums.length;
        int[] max = new int[length];
        max[0] = 1;

        int maxValue = 1;
        for (int i = 1; i < length; i++) {
            max[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    max[i] = Math.max(max[i], max[j] + 1);
                }
            }
            maxValue = Math.max(maxValue, max[i]);
        }
        return maxValue;
    }

    @Test
    public void T17() {
        int[] arr = new int[]{1, 2, 5, 10};
        int amount = 38;

        System.out.println(coinChange(arr, amount));
    }

    public int coinChange(int[] coins, int amount) {
        int[] result = new int[amount + 1];
        Arrays.fill(result, amount + 1);
        result[0] = 0;
        for (int i = 1; i <= amount; i++) {

            for (int j = 0; j < coins.length; j++) {
                if (i >= coins[j]) {
                    result[i] = Math.min(result[i], result[i - coins[j]] + 1);
                }
            }
        }
        return result[amount] > amount ? -1 : result[amount];
    }


    @Test
    public void sort1() {
        int[] arr = new int[]{2, 45, 5, 1, 21, 12, 8};
        System.out.println(Arrays.toString(insertSort(arr)));
    }

    public int[] insertSort(int[] coins) {
        for (int i = 0; i < coins.length; i++) {
            for (int j = i; j > 0 && coins[j] < coins[j - 1]; j--) {
                int temp = coins[j - 1];
                coins[j - 1] = coins[j];
                coins[j] = temp;
            }
        }
        return coins;
    }

    @Test
    public void sort2() {
        int[] arr = new int[]{2, 45, 5, 1, 21, 12, 8, 451, 12};
        xSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public void xSort(int[] coins, int low, int high) {
        if (low >= high) {
            return;
        }
        int middle = low + (high - low) / 2;
        xSort(coins, low, middle);
        xSort(coins, middle, high);
    }

    public int kthToLast(ListNode head, int k) {
        ListNode end = head;
        for (int i = 0; i < k; i++) {
            end = end.next;
        }

        while (end != null) {
            head = head.next;
            end = end.next;
        }
        return head.val;
    }

    @Test
    public void test110() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
//        ListNode l4 = new ListNode(4);
//        ListNode l5 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
//        l3.next = l4;
//        l4.next = l5;
        ListNode listNode = rotateRight(l1, 4);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        ListNode end = head;
        ListNode tail = head;
        for (int i = 0; i < k; i++) {
            if (end.next == null) {
                end = head;
            } else {
                end = end.next;
            }
        }

        while (end.next != null) {
            end = end.next;
            tail = tail.next;
        }
        end.next = head;
        ListNode next = tail.next;
        tail.next = null;
        return next;
    }

    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        Node tail = head;
        while (tail != null) {
            if (tail.random != null) {
                tail.random = new Node(tail.random.val);
            }
            tail = tail.next;
        }

        Node first = new Node(head.val);
        first.random = head.random;

        Node cur = first;
        while (head.next != null) {
            head = head.next;
            cur.next = new Node(head.val);
            cur.random = head.random;
            cur = cur.next;
        }
        return first;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();
        while (l1 != null) {
            s1.push(l1.val);
            l1 = l1.next;
        }

        while (l2 != null) {
            s2.push(l2.val);
            l2 = l2.next;
        }

        ListNode head = null;
        ListNode cur = null;
        int jin = 0;
        while (!s1.isEmpty() || !s2.isEmpty() || jin != 0) {
            int v1;
            if (s1.isEmpty()) {
                v1 = 0;
            } else {
                v1 = s1.pop();
            }
            int v2;
            if (s2.isEmpty()) {
                v2 = 0;
            } else {
                v2 = s2.pop();
            }

            int value = (v1 + v2 + jin) % 10;
            cur = new ListNode(value);
            cur.next = head;
            head = cur;
            jin = (v1 + v2 + jin) / 10;
        }
        return cur;
    }

    public boolean isPalindrome(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int i = 0, j = list.size() - 1;
        while (j > i) {
            if (!list.get(i).equals(list.get(j))) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    @Test
    public void test1110() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(1);
        ListNode l3 = new ListNode(3);
        ListNode l31 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l41 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
        l3.next = l31;
        l31.next = l4;
        l4.next = l41;
        l41.next = l5;
        ListNode listNode = deleteDuplicates(l1);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }


    public ListNode deleteDuplicates(ListNode head) {
        ListNode pre = new ListNode(0);
        pre.next = head;

        ListNode left;
        ListNode right;

        ListNode temp = pre;

        while (temp.next != null) {
            left = temp.next;
            right = left;
            while (right.next != null && right.next.val == left.val) {
                right = right.next;
            }
            if (left != right) {
                temp.next = right.next;
            } else {
                temp = temp.next;
            }
        }
        return pre.next;
    }


//    public ListNode detectCycle(ListNode head) {
//        if (head == null || head.next == null) {
//            return null;
//        }
//        Set<ListNode> set = new HashSet<>();
//        ListNode cur = head;
//        while (cur != null) {
//            if (!set.add(cur)) {
//                return cur;
//            }
//            cur = cur.next;
//        }
//        return null;
//    }

    @Test
    public void Test() {
        ListNode listNode = new ListNode(4);
        ListNode listNode1 = new ListNode(3);
        ListNode listNode2 = new ListNode(0);
        ListNode listNode3 = new ListNode(-4);
        listNode.next = listNode1;
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode1;
        System.out.println(detectCycle(listNode).val);
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode fast = head;
        ListNode slow = head;

        while (slow.next != null) {
            fast = fast.next;
            slow = slow.next.next;
            if (fast == slow) {
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }

    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode l2 = list2;
        while (l2 != null && l2.next != null) {
            l2 = l2.next;
        }

        ListNode pre = new ListNode(-1);
        pre.next = list1;

        ListNode la = pre;
        ListNode lb = pre;
        for (int i = 0; i <= b + 1; i++) {
            if (i < a) {
                la = la.next;
            }
            lb = lb.next;
        }
        if (l2 != null) {
            l2.next = lb;
        }
        la.next = list2;
        return pre.next;
    }

    @Test
    public void test11110() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l31 = new ListNode(4);
//        ListNode l4 = new ListNode(5);
//        ListNode l41 = new ListNode(6);
//        ListNode l5 = new ListNode(7);
        l1.next = l2;
        l2.next = l3;
        l3.next = l31;
//        l31.next = l4;
//        l4.next = l41;
//        l41.next = l5;
        ListNode[] listNode = splitListToParts(l1, 5);
        for (ListNode node : listNode) {
            System.out.println(node.val);
        }
    }

    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] result = new ListNode[k];
        if (k == 0 || k == 1) {
            result[0] = root;
            return result;
        }

        ListNode temp = root;
        int count = 0;
        while (temp != null) {
            temp = temp.next;
            count++;
        }

        int value = count / k;
        int v2 = count % k;

        ListNode k1 = root;
        for (int j = 0; j < k; j++) {
            if (k1 != null) {
                result[j] = k1;
                for (int l = 0; l < value + ((v2 > j) ? 1 : 0); l++) {
                    if (l == value - 1 + ((v2 > j) ? 1 : 0)) {
                        ListNode end = k1;
                        k1 = k1.next;
                        end.next = null;
                    } else {
                        k1 = k1.next;
                    }
                }
            }
        }
        return result;
    }

    @Test
    public void test1111() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;

        ListNode l11 = new ListNode(1);
        ListNode l21 = new ListNode(3);
        ListNode l31 = new ListNode(4);
        ListNode l41 = new ListNode(5);
        l11.next = l21;
        l21.next = l31;
        l31.next = l41;
        ListNode listNode = mergeTwo(l1, l11);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public ListNode reorderList(ListNode head) {
        ListNode tail = head;
        List<ListNode> listNodes = new ArrayList<>();

        while (tail != null) {
            ListNode next = tail.next;
            tail.next = null;
            listNodes.add(tail);
            tail = next;
        }

        int i = 0, j = listNodes.size() - 1;
        while (i < j) {
            listNodes.get(i++).next = listNodes.get(j);
            if (i == j) {
                break;
            }
            listNodes.get(j--).next = listNodes.get(i);
        }
        return head;

    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        List<ListNode> listNodes = new ArrayList<>();
        Collections.addAll(listNodes, lists);

        while (listNodes.size() > 1) {
            listNodes.remove(0);
            ListNode listNode = mergeTwo(listNodes.remove(0), listNodes.remove(0));
            listNodes.add(listNode);
        }
        return listNodes.get(0);
    }

    private ListNode mergeTwo(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(-1);
        ListNode cur = pre;

        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                cur.next = l2;
                l2 = l2.next;
            } else {
                cur.next = l1;
                l1 = l1.next;
            }
            cur = cur.next;
        }
        if (l1 != null) {
            cur.next = l1;
        }
        if (l2 != null) {
            cur.next = l2;
        }
        return pre.next;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode l1 = headA;
        ListNode l2 = headB;

        while (l1 != l2) {
            l1 = l1 == null ? headB : l1.next;
            l2 = l2 == null ? headA : l2.next;
        }
        return l1;
    }

    @Test
    public void Test1() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        ListNode listNode = oddEvenList(l1);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }

    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode pre1 = new ListNode(-1);
        ListNode pre2 = new ListNode(-1);
        pre1.next = head;
        pre2.next = head.next;

        ListNode cur1 = head;
        ListNode cur2 = pre2;


        ListNode end = cur1;
        while (cur1 != null) {
            cur2.next = cur1.next;
            cur2 = cur1.next;
            if (cur2 == null) {
                break;
            }
            cur1.next = cur2.next;
            cur1 = cur2.next;
            end = cur1 == null ? end : cur1;
        }
        if (end != null) {
            end.next = pre2.next;
        }
        return pre1.next;
    }

    //    public boolean isPalindrome(ListNode head) {
//        List<Integer> list = new ArrayList<>();
//
//        while (head != null) {
//            list.add(head.val);
//            head = head.next;
//        }
//        int first = 0;
//        int lastIndex = list.size() - 1;
//        while (first < lastIndex) {
//            if (list.get(first).intValue() != list.get(lastIndex).intValue()){
//                return false;
//            }
//            first++;
//            lastIndex--;
//        }
//        return true;
//    }

    public boolean isSubPath(ListNode head, TreeNode root) {
        if (head == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        //先判断当前的节点，如果不对，再看左子树和右子树呗
        return isSub(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    public boolean isSub(ListNode cur, TreeNode root) {
        if (cur == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        if (cur.val == root.val) {
            if (isSub(cur.next, root.left) || isSub(cur.next, root.right)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void Test11() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(-3);
        ListNode l5 = new ListNode(1);
//        ListNode l6 = new ListNode(2);
//        ListNode l7 = new ListNode(1);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
//        l5.next = l6;
//        l6.next = l7;
        ListNode partition = removeZeroSumSublists(l1);
        while (partition != null) {
            System.out.println(partition.val);
            partition = partition.next;
        }
    }

    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;

        ListNode cur = head;
        ListNode next = head.next;
        while (next != null) {
            if (next.val < x) {
                cur.next = next.next;

                next.next = pre.next;
                pre.next = next;
                next = cur.next;
            } else {
                next = next.next;
                cur = cur.next;
            }
        }
        return pre.next;
    }

    public int[] nextLargerNodes(ListNode head) {
        if (head == null) {
            return new int[]{0};
        }
        List<Integer> list = new ArrayList<>();

        ListNode start = head;
        ListNode next;
        while (start != null) {
            next = start.next;
            while (next != null && next.val <= start.val) {
                next = next.next;
            }
            list.add(next == null ? 0 : next.val);
            start = start.next;
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public ListNode removeZeroSumSublists(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode pre = new ListNode(0);
        pre.next = head;

        int value;
        ListNode cur = pre;
        ListNode next;

        while (cur != null) {
            value = 0;
            next = cur.next;
            while (next != null && (value += next.val) != 0) {
                next = next.next;
            }
            if (value == 0 && next != null) {
                cur.next = next.next;
            }

            cur = cur.next;
        }
        return pre.next;
    }


    @Test
    public void Test1111() {
        int[] arr = new int[]{-1, 2, 1, -4};
        System.out.println(threeSumClosest(arr, 1));
    }

    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int ans = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length; i++) {
            int start = i + 1;
            int end = nums.length - 1;
            while (start < end) {
                int sum = nums[start] + nums[end] + nums[i];
                if (Math.abs(target - sum) < Math.abs(target - ans))
                    ans = sum;
                if (sum > target)
                    end--;
                else if (sum < target)
                    start++;
                else
                    return ans;
            }
        }

        return ans;
    }

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>();
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        StringBuilder result = new StringBuilder();
        ddd(digits, 0, phoneMap, result, list);
        return list;
    }

    public void ddd(String digits, int index, Map<Character, String> map, StringBuilder result, List<String> list) {
        if (index == digits.length()) {
            list.add(result.toString());
        } else {
            String s = map.get(digits.charAt(index));
            for (int i = 0; i < s.length(); i++) {
                result.append(s.charAt(i));
                ddd(digits, index + 1, map, result, list);
                result.deleteCharAt(index);
            }
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        ListNode hair = new ListNode(0);
        hair.next = head;
        ListNode pre = hair;


        while (head!=null){
            ListNode cur = head;
            for (int i = 0; i < k; i++) {
                head = head.next;
                if (head == null){
                    return hair.next;
                }
            }
        }
        return pre.next;
    }

    public ListNode revert(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

}
