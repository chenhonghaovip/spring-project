package com.honghao.cloud.orderapi.algorithm.sort;

import lombok.Data;

/**
 * @author chenhonghao
 * @date 2019-10-14 17:09
 */
@Data
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int x) { val = x; }
}
