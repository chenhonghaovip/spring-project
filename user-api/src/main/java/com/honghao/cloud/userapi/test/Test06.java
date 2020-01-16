package com.honghao.cloud.userapi.test;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author chenhonghao
 * @date 2020-01-16 19:59
 */
public class Test06 {
    @Data
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(0);
        treeNode.setLeft(new TreeNode(1));
        treeNode.getLeft().setLeft(new TreeNode(3));
        treeNode.getLeft().setRight(new TreeNode(4));
        treeNode.setRight(new TreeNode(2));
        treeNode.getRight().setLeft(new TreeNode(5));
        treeNode.getRight().setRight(new TreeNode(6));
        treeNode.getRight().getLeft().setLeft(new TreeNode(7));
        inorderTraversal(treeNode);

    }
    private static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;

        while (!stack.isEmpty() || treeNode!=null) {
            while (treeNode!=null){
                stack.push(treeNode);
                treeNode = treeNode.getLeft();
            }
            treeNode = stack.pop();
            list.add(treeNode.getVal());
            System.out.println(treeNode.getVal());

            treeNode = treeNode.getRight();

        }
        return list;
    }
}
