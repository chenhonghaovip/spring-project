package com.honghao.cloud.userapi.test.designpatterns.builder;

/**
 * 具体建造者：实现了抽象建造者接口。
 *
 * @author chenhonghao
 * @date 2020-02-29 21:04
 */
public class ConcreteBuilder extends AbstractBuilder{
    @Override
    public AbstractBuilder buildPartA() {
        product.setPartA("建造PartA");
        return this;
    }

    @Override
    public AbstractBuilder buildPartB() {
        product.setPartB("建造PartB");
        return this;
    }

    @Override
    public AbstractBuilder buildPartC() {
        product.setPartC("建造PartC");
        return this;
    }
}
