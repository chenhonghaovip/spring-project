package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.accountapi.service.RabbitService;
import com.honghao.cloud.accountapi.template.RabbitTemplateService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
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
public class RabbitController {
    @Resource
    private RabbitService rabbitService;
    @Resource
    private RabbitTemplateService rabbitTemplateService;

    @PostMapping("/business")
    public BaseResponse business(@RequestBody ShopInfo shopInfo){
        return rabbitTemplateService.sendMessage(shopInfo, "test", () -> rabbitService.saveShop(shopInfo));
    }
}
