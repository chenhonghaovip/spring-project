package com.honghao.cloud.userapi.test.designpatterns.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 指挥者：调用建造者中的方法完成复杂对象的创建。
 *
 * @author chenhonghao
 * @date 2020-02-29 21:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Director {
    private AbstractBuilder abstractBuilder;

    /**
     * 产品构建与组装方法
     * @return Product
     */
    Product construct() {
        abstractBuilder.buildPartA();
        abstractBuilder.buildPartB();
        abstractBuilder.buildPartC();
        return abstractBuilder.getResult();
    }
}
