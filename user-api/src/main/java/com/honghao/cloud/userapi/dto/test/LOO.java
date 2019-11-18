package com.honghao.cloud.userapi.dto.test;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhonghao
 * @date 2019-11-16 18:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LOO {
    @JSONField(name = "name_1")
    private String name;

    @JSONField(name = "name_11")
    private String age;
}
