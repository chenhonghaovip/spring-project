package com.honghao.cloud.accountapi.controller;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.accountapi.config.RabbitConfig;
import com.honghao.cloud.accountapi.dto.request.MsgInfoDTO;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.dto.ProtocolConstants;
import com.honghao.cloud.basic.common.netty.NettyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

/**
 * netty控制处理
 *
 * @author chenhonghao
 * @date 2020-10-22 14:48
 */
@RestController
@RequestMapping("/nettyController")
public class NettyController {

    @PostMapping("/netty")
    public BaseResponse netty(){
        MsgInfoDTO msgInfoDTO = MsgInfoDTO.builder().businessId("111111").content(JSON.toJSONString("werqwrqw"))
                .status(0).topic(RabbitConfig.CREATE_ORDER).appId("4214124").url("/order/batchQuery").build();

        try {
            return NettyUtils.sendMessage(msgInfoDTO, ProtocolConstants.INSERT);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
