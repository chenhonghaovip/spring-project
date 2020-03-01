package com.honghao.cloud.userapi.test.designpatterns.builder;

import lombok.Data;

/**
 * 产品类
 *
 * @author chenhonghao
 * @date 2020-02-29 20:58
 */

@Data
public class Product {
    private String partA;
    private String partB;
    private String partC;
    public void show() {
        System.out.println("Product{" +
                "partA='" + partA + '\'' +
                ", partB='" + partB + '\'' +
                ", partC='" + partC + '\'' +
                '}');
    }
}
