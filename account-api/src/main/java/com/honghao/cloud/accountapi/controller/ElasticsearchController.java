package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("/create")
    public BaseResponse create(){
        ShopInfo shopInfo = ShopInfo.builder().shopName("test").shopId("12345").build();
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(shopInfo.getShopId())
                .withObject(shopInfo)
                .build();

        // 存入索引，返回文档ID
        String documentId = elasticsearchTemplate.index(indexQuery);
        System.out.println(documentId);
        List<IndexQuery> list = new ArrayList<>();
        elasticsearchTemplate.bulkIndex(list);
        elasticsearchTemplate.createIndex(ShopInfo.class);
        // 类型映射 - 自定义映射
        elasticsearchTemplate.putMapping(ShopInfo.class);
        return BaseResponse.success();
    }
}
