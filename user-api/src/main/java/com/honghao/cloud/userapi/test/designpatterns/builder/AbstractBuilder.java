package com.honghao.cloud.userapi.test.designpatterns.builder;

/**
 * 抽象建造者：包含创建产品各个子部件的抽象方法。
 *
 * @author chenhonghao
 * @date 2020-02-29 21:02
 */
abstract class AbstractBuilder {
    /**
     * 创建产品对象
     */
    Product product=new Product();
    public abstract AbstractBuilder buildPartA();
    public abstract AbstractBuilder buildPartB();
    public abstract AbstractBuilder buildPartC();

    /**
     * 返回产品对象
     * @return Product
     */
    public Product getResult() {
        return product;
    }
}
