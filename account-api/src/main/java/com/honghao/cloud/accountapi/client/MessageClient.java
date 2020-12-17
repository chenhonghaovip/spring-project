package com.honghao.cloud.accountapi.client;

import com.honghao.cloud.accountapi.client.hystrix.MessageFallbackFactory;
import com.honghao.cloud.accountapi.dto.request.MsgInfoDTO;
import com.honghao.cloud.basic.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 账户服务接口
 *
 * @author chenhonghao
 * @date 2019-07-31 13:19
 */
@FeignClient(name = MessageClient.SERVICE_ID, fallbackFactory = MessageFallbackFactory.class)
public interface MessageClient {
    String SERVICE_ID = "ReliableMessagingService";

    /**
     * 存入消息表
     *
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @PostMapping("/messageController/message")
    BaseResponse saveMessage(@RequestBody MsgInfoDTO msgInfoDTO);

    /**
     * 修改消息状态
     *
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @PutMapping("/messageController/message")
    BaseResponse send(@RequestBody MsgInfoDTO msgInfoDTO);

    /**
     * 修改消息状态
     *
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @DeleteMapping("/messageController/message")
    BaseResponse delete(@RequestBody MsgInfoDTO msgInfoDTO);

    /**
     * 消息处理完成
     *
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @PutMapping("/messageController/complete")
    BaseResponse complete(@RequestBody MsgInfoDTO msgInfoDTO);
}
