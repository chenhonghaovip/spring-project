package com.honghao.cloud.userapi.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * node
 *
 * @author chenhonghao
 * @date 2019-12-17 15:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Node {
    private String value;
    private Node node;
}
