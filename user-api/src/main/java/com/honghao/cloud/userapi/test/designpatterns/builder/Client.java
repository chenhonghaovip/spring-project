package com.honghao.cloud.userapi.test.designpatterns.builder;

/**
 * 客户类
 *
 * @author chenhonghao
 * @date 2020-02-29 21:11
 */
public class Client {
    public static void main(String[] args) {
        AbstractBuilder builder = new ConcreteBuilder();
        builder = builder.buildPartA().buildPartB().buildPartC();
        Product product = builder.getResult();
        product.show();
    }
}
