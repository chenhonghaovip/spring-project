package com.honghao.cloud.message.controller;

import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.message.domain.entity.MsgInfo;
import com.honghao.cloud.message.dto.BatchMsgInfoDTO;
import com.honghao.cloud.message.dto.MsgInfoDTO;
import com.honghao.cloud.message.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息控制中心
 *
 * @author chenhonghao
 * @date 2020-07-30 16:19
 */
@RestController
@RequestMapping("/messageController")
public class MessageController {
    @Resource
    private MessageService messageService;

    /**
     * 预发布消息
     *
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @PostMapping("/message")
    public BaseResponse saveMessage(@RequestBody MsgInfoDTO msgInfoDTO) {
        return messageService.saveMessage(msgInfoDTO);
    }

    /**
     * 发送消息到队列
     *
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @PutMapping("/message")
    public BaseResponse send(@RequestBody MsgInfoDTO msgInfoDTO) {
        return messageService.send(msgInfoDTO);
    }

    /**
     * 批量预发布消息
     *
     * @param batchMsgInfoDTO batchMsgInfoDTO
     * @return BaseResponse
     */
    @PostMapping("/batchMessage")
    public BaseResponse batchSaveMessage(@RequestBody BatchMsgInfoDTO batchMsgInfoDTO) {
        return messageService.batchSaveMessage(batchMsgInfoDTO);
    }

    /**
     * 批量发送消息到队列
     *
     * @param batchMsgInfoDTO batchMsgInfoDTO
     * @return BaseResponse
     */
    @PutMapping("/batchMessage")
    public BaseResponse batchSend(@RequestBody BatchMsgInfoDTO batchMsgInfoDTO) {
        return messageService.batchSend(batchMsgInfoDTO);
    }

    /**
     * 消息处理完成
     *
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @PutMapping("/complete")
    public BaseResponse complete(@RequestBody MsgInfoDTO msgInfoDTO) {
        return messageService.complete(msgInfoDTO);
    }

    /**
     * 查询消息状态
     *
     * @param messageId messageId
     * @return BaseResponse
     */
    @GetMapping("/message")
    public BaseResponse<MsgInfo> selectMessage(@RequestParam("messageId") long messageId) {
        return messageService.selectMessage(messageId);
    }

    public void ddd() {
        System.out.println("1234444");
    }
}
