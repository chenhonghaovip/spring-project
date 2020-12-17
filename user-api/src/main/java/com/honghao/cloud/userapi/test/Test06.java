package com.honghao.cloud.userapi.test;

import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenhonghao
 * @date 2020-01-16 19:59
 */
public class Test06 {
    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(0);
        treeNode.setLeft(new TreeNode(1));
        treeNode.getLeft().setLeft(new TreeNode(3));
        treeNode.getLeft().setRight(new TreeNode(4));
        treeNode.setRight(new TreeNode(2));
        treeNode.getRight().setLeft(new TreeNode(5));
        treeNode.getRight().setRight(new TreeNode(6));
        treeNode.getRight().getLeft().setLeft(new TreeNode(7));
        List<Integer> middleList = inorderTraversal(treeNode);
        System.out.println(middleList);

        List<Integer> firstList = firstTraversal(treeNode);
        System.out.println(firstList);

        List<Integer> lastList = lastTraversal(treeNode);
        System.out.println(lastList);

        getInfo();
    }

    private static void getInfo() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(5);
        concurrentHashMap.put("1", "1");
        int[] a = new int[5];
        a[0] = 1;
        System.out.println(a.length);
        System.out.println(concurrentHashMap.size());
    }

    /**
     * 先序遍历
     */
    private static List<Integer> firstTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;
        stack.push(treeNode);

        while (!stack.isEmpty()) {
            treeNode = stack.pop();
            list.add(treeNode.getVal());
            if (treeNode.getRight() != null) {
                stack.push(treeNode.getRight());
            }
            if (treeNode.getLeft() != null) {
                stack.push(treeNode.getLeft());
            }
        }
        return list;
    }

    /**
     * 中序遍历
     */
    private static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;

        while (!stack.isEmpty() || treeNode != null) {
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.getLeft();
            }
            treeNode = stack.pop();
            list.add(treeNode.getVal());
            treeNode = treeNode.getRight();
        }
        return list;
    }

    /**
     * 后序遍历
     */
    private static List<Integer> lastTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;

        Set<TreeNode> set = new HashSet<>();

        while (!stack.isEmpty() || treeNode != null) {
            while (treeNode != null && !set.contains(treeNode)) {
                stack.push(treeNode);
                treeNode = treeNode.getLeft();
            }
            treeNode = stack.peek();
            if (treeNode.getRight() == null || set.contains(treeNode)) {
                treeNode = stack.pop();
                set.add(treeNode);
                list.add(treeNode.getVal());
                if (stack.isEmpty()) {
                    return list;
                }
                treeNode = stack.peek();
                treeNode = treeNode.getRight();
            } else {
                set.add(treeNode);
                treeNode = treeNode.getRight();
            }
        }
        return list;
    }

    @Data
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
