package com.honghao.cloud.orderapi.dto.test;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhonghao
 * @date 2019-11-16 17:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    /**
     * name_1 : name
     */

    @JSONField(name = "name_1")
    private String name1;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }
}
