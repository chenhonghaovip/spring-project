package com.honghao.cloud.accountapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.utils.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
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
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 搜索引擎控制中心
 *
 * @author chenhonghao
 * @date 2020-09-21 13:40
 */
@Slf4j
@RestController
@RequestMapping("/elasticsearchController")
@Api(tags = "搜索引擎控制中心", value = "搜索引擎控制中心")
public class ElasticsearchController {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     *
     * @return BaseResponse
     */
    @PostMapping("/createIndex")
    @ApiOperation(value = "创建索引", notes = "创建索引")
    public BaseResponse create(@RequestParam("index") String index) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        createIndexRequest.alias(new Alias("honghao"));
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BaseResponse.successData(createIndexResponse);
    }

    /**
     * 通过别名获取索引,判断索引是否存在
     *
     * @return BaseResponse
     */
    @GetMapping("/getAliasIndex")
    @ApiOperation(value = "通过别名获取索引,判断索引是否存在", notes = "通过别名获取索引,判断索引是否存在")
    public BaseResponse getAliasIndex(@RequestParam("alias") String alias) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(alias);

        try {
            GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
            return BaseResponse.success();
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    /**
     * 获取索引,判断索引是否存在
     *
     * @return BaseResponse
     */
    @GetMapping("/getIndex")
    @ApiOperation(value = "获取索引,判断索引是否存在", notes = "获取索引,判断索引是否存在")
    public BaseResponse getIndex(@RequestParam("index") String index) {
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
     *
     * @return BaseResponse
     */
    @DeleteMapping("/deleteIndex")
    @ApiOperation(value = "删除索引", notes = "删除索引")
    public BaseResponse deleteIndex(@RequestParam("index") String index) {
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
     *
     * @return BaseResponse
     */
    @PostMapping("/createDocument")
    @ApiOperation(value = "创建文档", notes = "创建文档")
    public BaseResponse createDocument(@RequestBody ShopInfo shopInfo) {
        // 创建请求
        IndexRequest indexRequest = new IndexRequest("222");
        indexRequest.id("2");

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
     *
     * @return BaseResponse
     */
    @GetMapping("/getDocument")
    @ApiOperation(value = "获取文档", notes = "获取文档")
    public BaseResponse getDocument(@RequestParam("index") String index, @RequestParam("id") String id) {
        GetRequest getRequest = new GetRequest(index, id);
        try {
            GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(documentFields);
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    /**
     * 更新文档
     *
     * @return BaseResponse
     */
    @PostMapping("/updateDocument")
    @ApiOperation(value = "更新文档", notes = "更新文档")
    public BaseResponse updateDocument(@RequestBody ShopInfo shopInfo) {
        UpdateRequest updateRequest = new UpdateRequest("test", "1");
        updateRequest.doc(JSON.toJSONString(shopInfo), XContentType.JSON);
        try {
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(update);
        } catch (IOException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    /**
     * 删除文档
     *
     * @return BaseResponse
     */
    @DeleteMapping("/deleteDocument")
    @ApiOperation(value = "删除文档", notes = "删除文档")
    public BaseResponse deleteDocument(@RequestParam("index") String index, @RequestParam("id") String id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
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
     *
     * @return BaseResponse
     */
    @PostMapping("/batchInsertDocument")
    @ApiOperation(value = "批量插入文档", notes = "批量插入文档")
    public BaseResponse batchInsertDocument() {
        int k = 1;
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        List<ShopInfo> objects = new ArrayList<>();
        objects.add(ShopInfo.builder().shopName("1").shopId("1" + k).build());
        objects.add(ShopInfo.builder().shopName("2").shopId("2" + k).build());
        objects.add(ShopInfo.builder().shopName("3").shopId("3" + k).build());
        objects.add(ShopInfo.builder().shopName("4").shopId("4" + k).build());

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
     *
     * @return BaseResponse
     */
    @GetMapping("/search")
    @ApiOperation(value = "复杂查询模式", notes = "复杂查询模式")
    public BaseResponse search(@RequestParam("index") String index) {
        // 查询全部
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        System.out.println(matchAllQueryBuilder);

        // 精确匹配
        TermQueryBuilder termQuery = QueryBuilders.termQuery("shopName", "string");

        return commonSearch(index, new BoolQueryBuilder().filter(termQuery));
    }

    /**
     * @return BaseResponse
     */
    @PostMapping("/init")
    @ApiOperation(value = "批次插入文档", notes = "批次插入文档")
    public BaseResponse init() {
        String index = "order_info";
        String s = HttpUtil.doPost("http://127.0.0.1:8102/client/deliveryController/getInfo", 10);
        List<WaybillBcList> lists = JSONArray.parseArray(s, WaybillBcList.class);
        BulkRequest bulkRequest = new BulkRequest();

        lists.forEach(each -> bulkRequest.add(new IndexRequest(index).id(each.getWId()).source(JSON.toJSONString(each), XContentType.JSON)));

        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return BaseResponse.successData(!bulk.hasFailures());
        } catch (IOException e) {
            log.error(e.getMessage());
            return BaseResponse.error(e.getMessage());
        }
    }

    @GetMapping("/queryOrder")
    public BaseResponse orderInfo() {
        String index = "create_order";
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("receiveAdcode", "340202"))
                .filter(QueryBuilders.termQuery("wId", "2020052500000408"));
        return commonSearch(index, boolQueryBuilder);
    }

    /**
     * 公共服务部分
     *
     * @param index            查询索引
     * @param boolQueryBuilder 查询条件
     * @return BaseResponse
     */
    private BaseResponse commonSearch(String index, BoolQueryBuilder boolQueryBuilder) {
        // 构建搜索请求
        SearchRequest searchRequest = new SearchRequest(index);
        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(5, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<Map<String, Object>> collect = Arrays.stream(search.getHits().getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
            return BaseResponse.successData(collect);
        } catch (IOException e) {
            log.error(e.getMessage());
            return BaseResponse.error(e.getMessage());
        }
    }
}
