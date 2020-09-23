package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 搜索引擎控制中心
 *
 * @author chenhonghao
 * @date 2020-09-21 13:40
 */
@Slf4j
@RestController
@RequestMapping("/elasticsearchController")
@Api(tags = "搜索引擎控制中心",value = "搜索引擎控制中心")
public class ElasticsearchController {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    @PostMapping("/create")
    public BaseResponse create(){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("account_api");
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(createIndexResponse);
        return BaseResponse.successData(createIndexResponse);
    }
}
