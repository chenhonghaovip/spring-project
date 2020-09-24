package com.honghao.cloud.accountapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.utils.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    /**
     * 创建索引
     * @return BaseResponse
     */
    @PostMapping("/createIndex")
    @ApiOperation(value = "创建索引" ,notes = "创建索引")
    public BaseResponse create(@RequestParam("index") String index){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BaseResponse.successData(createIndexResponse);
    }

    /**
     * 获取索引,判断索引是否存在
     * @return BaseResponse
     */
    @GetMapping("/getIndex")
    @ApiOperation(value = "获取索引,判断索引是否存在" ,notes = "获取索引,判断索引是否存在")
    public BaseResponse getIndex(@RequestParam("index") String index){
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);

        try {
            boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(exists);
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    /**
     * 删除索引
     * @return BaseResponse
     */
    @DeleteMapping("/deleteIndex")
    @ApiOperation(value = "删除索引" ,notes = "删除索引")
    public BaseResponse deleteIndex(@RequestParam("index") String index){
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        try {
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(delete.isAcknowledged());
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    /**
     * 创建文档
     * @return BaseResponse
     */
    @PostMapping("/createDocument")
    @ApiOperation(value = "创建文档" ,notes = "创建文档")
    public BaseResponse createDocument(@RequestBody ShopInfo shopInfo){
        // 创建请求
        IndexRequest indexRequest = new IndexRequest("test");
        indexRequest.id("1");

        // 将对象放入到请求中
        indexRequest.source(JSON.toJSONString(shopInfo), XContentType.JSON);

        try {
            IndexResponse index1 = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(index1);
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }


    /**
     * 获取文档
     * @return BaseResponse
     */
    @GetMapping("/getDocument")
    @ApiOperation(value = "获取文档" ,notes = "获取文档")
    public BaseResponse getDocument(){
        GetRequest getRequest = new GetRequest("test","1");
        try {
            GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(documentFields);
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    /**
     * 更新文档
     * @return BaseResponse
     */
    @PostMapping("/updateDocument")
    @ApiOperation(value = "更新文档" ,notes = "更新文档")
    public BaseResponse updateDocument(@RequestBody ShopInfo shopInfo){
        UpdateRequest updateRequest = new UpdateRequest("test","1");
        updateRequest.doc(JSON.toJSONString(shopInfo),XContentType.JSON);
        try {
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(update);
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    /**
     * 删除文档
     * @return BaseResponse
     */
    @DeleteMapping("/deleteDocument")
    @ApiOperation(value = "删除文档" ,notes = "删除文档")
    public BaseResponse deleteDocument(){
        DeleteRequest deleteRequest = new DeleteRequest("test","1");
        try {
            DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(delete);
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }


    /**
     * 批量插入文档(更新和删除类似，只要修改对应的请求类型就可以)
     * update UpdateRequest
     * delete DeleteRequest
     * @return BaseResponse
     */
    @PostMapping("/batchInsertDocument")
    @ApiOperation(value = "批量插入文档" ,notes = "批量插入文档")
    public BaseResponse batchInsertDocument(){
        int k = 1;
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        List<ShopInfo> objects = new ArrayList<>();
        objects.add(ShopInfo.builder().shopName("1").shopId("1"+k).build());
        objects.add(ShopInfo.builder().shopName("2").shopId("2"+k).build());
        objects.add(ShopInfo.builder().shopName("3").shopId("3"+k).build());
        objects.add(ShopInfo.builder().shopName("4").shopId("4"+k).build());

        for (ShopInfo object : objects) {
            bulkRequest.add(new IndexRequest("test").source(JSON.toJSONString(object), XContentType.JSON));
        }

        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(bulk);
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }


    /**
     * 复杂查询模式
     * SearchRequest 搜索请求
     * SearchSourceBuilder 搜索条件
     * @return BaseResponse
     */
    @GetMapping("/search")
    @ApiOperation(value = "复杂查询模式" ,notes = "复杂查询模式")
    public BaseResponse search(){
        // 构建搜索请求
        SearchRequest searchRequest = new SearchRequest("order_info");

        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 查询全部
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        System.out.println(matchAllQueryBuilder);

        // 精确匹配
        TermQueryBuilder termQuery = QueryBuilders.termQuery("receiveAdcode",  "310112");

        searchSourceBuilder.query(termQuery);
        searchSourceBuilder.timeout(new TimeValue(5, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(search.getHits());
        } catch (IOException e) {
            log.error(e.getMessage());
            return BaseResponse.error(e.getMessage());
        }
    }

    @PostMapping("/init")
    public BaseResponse init(){
        String index = "order_info";
        String s = HttpUtil.doPost("http://127.0.0.1:8102/client/deliveryController/getInfo", 10);
        List<WaybillBcList> lists = JSONArray.parseArray(s, WaybillBcList.class);
        BulkRequest bulkRequest = new BulkRequest();

        lists.forEach(each->bulkRequest.add(new IndexRequest(index).id(each.getWId()).source(JSON.toJSONString(each),XContentType.JSON)));

        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(!bulk.hasFailures());
        } catch (IOException e) {
            log.error(e.getMessage());
            return BaseResponse.error(e.getMessage());
        }
    }
}
