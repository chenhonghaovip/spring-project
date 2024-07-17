package com.honghao.cloud.middle.anno;

import lombok.Data;

@Data
public class TestDTO {

    @EsFieldMapping(alias = "name1")
    private String name;

    @EsFieldMapping(alias = "sex1")
    private String sex;

    private Integer age;

    private String address;
}
