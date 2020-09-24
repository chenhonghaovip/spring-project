package com.honghao.cloud.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.utils.SnowFlakeShortUrl;
import com.honghao.cloud.message.common.enums.MsgStatusEnum;
import com.honghao.cloud.message.component.MessageSender;
import com.honghao.cloud.message.domain.entity.MsgInfo;
import com.honghao.cloud.message.domain.mapper.MsgInfoMapper;
import com.honghao.cloud.message.dto.BatchMsgInfoDTO;
import com.honghao.cloud.message.dto.MsgInfoDTO;
import com.honghao.cloud.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 消息服务
 *
 * @author chenhonghao
 * @date 2020-09-24 15:40
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    private static SnowFlakeShortUrl snowFlake = new SnowFlakeShortUrl(2, 3);
    @Resource
    private MsgInfoMapper msgInfoMapper;
    @Resource
    private MessageSender messageSender;

    @Override
    public BaseResponse saveMessage(MsgInfoDTO msgInfoDTO) {
        long id = snowFlake.nextId();
        MsgInfo msgInfo = new MsgInfo();
        BeanUtils.copyProperties(msgInfoDTO,msgInfo);
        msgInfo.setMsgId(id);
        msgInfo.setStatus(MsgStatusEnum.TO_BE_CONFIRMED.getCode());
        msgInfo.setCreateTime(LocalDateTime.now());
        msgInfoMapper.insertSelective(msgInfo);
        return BaseResponse.successData(id);
    }

    @Override
    public BaseResponse send(MsgInfoDTO msgInfoDTO) {
        MsgInfo msgInfo = msgInfoMapper.selectByPrimaryKey(msgInfoDTO.getMsgId());
        msgInfo.setStatus(MsgStatusEnum.HAS_BEEN_SENT.getCode());
        msgInfoMapper.updateBatch(Collections.singletonList(msgInfoDTO.getMsgId()),MsgStatusEnum.HAS_BEEN_SENT.getCode());
        // 发送到消息队列
        messageSender.publicQueueProcessing(JSON.toJSONString(msgInfo),msgInfoDTO.getTopic());
        return BaseResponse.success();
    }

    @Override
    public BaseResponse batchSaveMessage(BatchMsgInfoDTO batchMsgInfoDTO) {
        LocalDateTime now = LocalDateTime.now();

        List<MsgInfo> list = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        batchMsgInfoDTO.getMsgList().forEach(each->{
            long id = snowFlake.nextId();
            ids.add(id);
            MsgInfo msgInfo = new MsgInfo();
            BeanUtils.copyProperties(each,msgInfo);
            msgInfo.setMsgId(id);
            msgInfo.setStatus(MsgStatusEnum.TO_BE_CONFIRMED.getCode());
            msgInfo.setCreateTime(now);
            list.add(msgInfo);
        });
        msgInfoMapper.batchInsertSelective(list);
        return BaseResponse.successData(ids);
    }

    @Override
    public BaseResponse batchSend(BatchMsgInfoDTO batchMsgInfoDTO) {
        List<MsgInfo> list = msgInfoMapper.selectBatch(batchMsgInfoDTO.getMsgIds());
        // 发送到消息队列
        list.forEach(each-> messageSender.publicQueueProcessing(JSON.toJSONString(each),each.getTopic()));
        // 批次更新消息记录
        msgInfoMapper.updateBatch(batchMsgInfoDTO.getMsgIds(),MsgStatusEnum.HAS_BEEN_SENT.getCode());
        return BaseResponse.success();
    }

    @Override
    public BaseResponse complete(MsgInfoDTO msgInfoDTO) {
        msgInfoMapper.updateBatch(Collections.singletonList(msgInfoDTO.getMsgId()),MsgStatusEnum.COMPLETED.getCode());
        return BaseResponse.success();
    }

    @Override
    public BaseResponse<MsgInfo> selectMessage(long messageId) {
        return BaseResponse.successData(msgInfoMapper.selectByPrimaryKey(messageId));
    }
}
