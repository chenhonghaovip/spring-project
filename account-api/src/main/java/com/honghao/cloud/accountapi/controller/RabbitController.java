package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.accountapi.service.RabbitService;
import com.honghao.cloud.accountapi.template.RabbitTemplateService;
import com.honghao.cloud.basic.common.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenhonghao
 * @date 2020-07-30 17:24
 */
@Slf4j
@RestController
@RequestMapping("/rabbitMqController")
@Api(value = "Rabbit测试使用", tags = "Rabbit测试使用")
public class RabbitController {
    @Resource
    private RabbitService rabbitService;
    @Resource
    private RabbitTemplateService rabbitTemplateService;

    /**
     * 可靠消息服务api
     *
     * @param shopInfo shopInfo
     * @return BaseResponse
     */
    @PostMapping("/business")
    @ApiOperation(value = "可靠消息服务", notes = "可靠消息服务")
    public BaseResponse business(@RequestBody ShopInfo shopInfo) {
        return rabbitTemplateService.sendMessage(shopInfo, "test", () -> rabbitService.saveShop(shopInfo));
    }

    /**
     * 可靠消息服务query
     *
     * @param data data
     * @return BaseResponse
     */
    @PostMapping("/query")
    @ApiOperation(value = "可靠消息服务查询", notes = "可靠消息服务查询")
    public BaseResponse query(@RequestBody String data) {
        return BaseResponse.successData(data);
    }
}
