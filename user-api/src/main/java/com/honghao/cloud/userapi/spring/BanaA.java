package com.honghao.cloud.userapi.spring;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chenhonghao
 * @date 2020-01-20 14:41
 */
@Data
@AllArgsConstructor
public class BanaA {
    private String name;

    public BanaA() {
        System.out.println();
    }
}
