package com.honghao.cloud.accountapi.dto;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author chenhonghao
 * @date 2020-09-21 16:46
 */
@Data
@Document(indexName = "testgoods",type = "goods")
public class GoodsInfo {
    private Long id;
    private String name;
    private String description;
}
