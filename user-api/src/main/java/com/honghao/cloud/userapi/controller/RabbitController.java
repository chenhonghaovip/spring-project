package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenhonghao
 * @date 2020-07-30 17:24
 */
@Slf4j
@RestController
@RequestMapping("/rabbitMqController")
@Api(value = "Rabbit测试使用" ,tags = "Rabbit测试使用")
public class RabbitController {


    /**
     * 可靠消息服务query
     * @param data data
     * @return BaseResponse
     */
    @PostMapping("/query")
    @ApiOperation(value = "可靠消息服务查询",notes = "可靠消息服务查询")
    public BaseResponse query(@RequestBody String data){
        return BaseResponse.successData(data);
    }
}
