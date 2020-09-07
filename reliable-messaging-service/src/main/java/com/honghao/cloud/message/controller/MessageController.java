package com.honghao.cloud.message.controller;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.utils.SnowFlakeShortUrl;
import com.honghao.cloud.message.common.enums.MsgStatusEnum;
import com.honghao.cloud.message.component.MessageSender;
import com.honghao.cloud.message.config.RabbitConfig;
import com.honghao.cloud.message.domain.entity.MsgInfo;
import com.honghao.cloud.message.domain.mapper.MsgInfoMapper;
import com.honghao.cloud.message.dto.MsgInfoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 消息控制中心
 *
 * @author chenhonghao
 * @date 2020-07-30 16:19
 */
@RestController
@RequestMapping("/messageController")
public class MessageController {
    private static SnowFlakeShortUrl snowFlake = new SnowFlakeShortUrl(2, 3);
    @Resource
    private MsgInfoMapper msgInfoMapper;
    @Resource
    private MessageSender messageSender;

    /**
     * 预发布消息
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @PostMapping("/message")
    public BaseResponse saveMessage(@RequestBody MsgInfoDTO msgInfoDTO){
        long id = snowFlake.nextId();
        MsgInfo msgInfo = new MsgInfo();
        BeanUtils.copyProperties(msgInfoDTO,msgInfo);
        msgInfo.setMsgId(id);
        msgInfo.setStatus(MsgStatusEnum.TO_BE_CONFIRMED.getCode());
        msgInfo.setCreateTime(new Date());
        msgInfoMapper.insertSelective(msgInfo);
        return BaseResponse.successData(id);
    }

    /**
     * 发送消息到队列
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @PutMapping("/message")
    public BaseResponse send(@RequestBody MsgInfoDTO msgInfoDTO){
        MsgInfo msgInfo = new MsgInfo();
        BeanUtils.copyProperties(msgInfoDTO,msgInfo);
        msgInfo.setStatus(MsgStatusEnum.HAS_BEEN_SENT.getCode());
        msgInfoMapper.updateByPrimaryKeySelective(msgInfo);
        // 发送到消息队列
        messageSender.publicQueueProcessing(msgInfo,RabbitConfig.TEST);
        return BaseResponse.success();
    }

    /**
     * 消息处理完成
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @PutMapping("/complete")
    public BaseResponse complete(@RequestBody MsgInfoDTO msgInfoDTO){
        MsgInfo msgInfo = new MsgInfo();
        BeanUtils.copyProperties(msgInfoDTO,msgInfo);
        msgInfo.setStatus(MsgStatusEnum.COMPLETED.getCode());
        msgInfoMapper.updateByPrimaryKeySelective(msgInfo);
        return BaseResponse.success();
    }

    /**
     * 查询消息状态
     * @param messageId messageId
     * @return BaseResponse
     */
    @GetMapping("/message")
    public BaseResponse<MsgInfo> selectMessage(@RequestParam("messageId") long messageId){
        return BaseResponse.successData(msgInfoMapper.selectByPrimaryKey(messageId));
    }
}
