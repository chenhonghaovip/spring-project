package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/create")
    public BaseResponse create(){

        return BaseResponse.success();
    }
}
