package com.honghao.cloud.orderapi.client;

import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.orderapi.client.hystrix.MessageFallbackFactory;
import com.honghao.cloud.orderapi.dto.common.BatchMsgInfoDTO;
import com.honghao.cloud.orderapi.dto.common.MsgInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


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
     * 删除消息
     *
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @DeleteMapping("/messageController/message")
    BaseResponse delete(@RequestBody MsgInfoDTO msgInfoDTO);

    /**
     * 批量预发布消息
     *
     * @param batchMsgInfoDTO batchMsgInfoDTO
     * @return BaseResponse
     */
    @PostMapping("/messageController/batchMessage")
    BaseResponse<List<Long>> batchSaveMessage(@RequestBody BatchMsgInfoDTO batchMsgInfoDTO);

    /**
     * 批量发送消息到队列
     *
     * @param batchMsgInfoDTO batchMsgInfoDTO
     * @return BaseResponse
     */
    @PutMapping("/messageController/batchMessage")
    BaseResponse batchSend(@RequestBody BatchMsgInfoDTO batchMsgInfoDTO);
}
