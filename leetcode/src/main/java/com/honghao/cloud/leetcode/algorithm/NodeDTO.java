package com.honghao.cloud.leetcode.algorithm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 节点
 *
 * @author chenhonghao
 * @date 2019-09-19 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class NodeDTO {
    private int begin;
    private int end;
    private int weight;
}
