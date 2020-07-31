package com.honghao.cloud.message.controller;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
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
    @Resource
    private MsgInfoMapper msgInfoMapper;
    @Resource
    private MessageSender messageSender;

    @PostMapping("/message")
    public BaseResponse saveMessage(@RequestBody MsgInfoDTO msgInfoDTO){
        MsgInfo msgInfo = new MsgInfo();
        BeanUtils.copyProperties(msgInfoDTO,msgInfo);
        msgInfo.setCreateTime(new Date());
        msgInfoMapper.insertSelective(msgInfo);
        return BaseResponse.success();
    }

    @PutMapping("/message")
    public BaseResponse send(@RequestBody MsgInfoDTO msgInfoDTO){
        MsgInfo msgInfo = new MsgInfo();
        BeanUtils.copyProperties(msgInfoDTO,msgInfo);
        msgInfoMapper.updateByPrimaryKeySelective(msgInfo);
        if (Integer.valueOf(1).equals(msgInfoDTO.getStatus())){
            messageSender.publicQueueProcessing(msgInfo,RabbitConfig.TEST);
        }
        return BaseResponse.success();
    }

    @GetMapping("/message")
    public BaseResponse<MsgInfo> selectMessage(@RequestParam("messageId") long messageId){
        return BaseResponse.successData(msgInfoMapper.selectByPrimaryKey(messageId));
    }
}
