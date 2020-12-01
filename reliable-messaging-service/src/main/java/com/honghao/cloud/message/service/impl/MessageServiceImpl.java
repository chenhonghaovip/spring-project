package com.honghao.cloud.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import com.honghao.cloud.basic.common.utils.SnowFlakeShortUrl;
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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private static final LinkedBlockingQueue<Long> SEND_BLOCKING_QUEUE = new LinkedBlockingQueue<>(10000);
    private static final LinkedBlockingQueue<Long> COMPLETE_BLOCKING_QUEUE = new LinkedBlockingQueue<>(10000);
    private static ScheduledThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1,"rabbitmq_sender");
    private static ScheduledThreadPoolExecutor threadPoolExecutor1 = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1,"complete");
    @Resource
    private MsgInfoMapper msgInfoMapper;
    @Resource
    private MessageSender messageSender;

    @PostConstruct
    public void init(){
        /*
         * 发送到消息队列并修改数据库状态
         */
        threadPoolExecutor.scheduleAtFixedRate(()->{
            int a;
            if ((a = SEND_BLOCKING_QUEUE.size())==0){
                return;
            }
            List<Long> msgIds = new ArrayList<>();
            for (int i = 0; i < a; i++) {
                Long l = SEND_BLOCKING_QUEUE.poll();
                msgIds.add(l);
            }
            business(msgIds);
        },1,1, TimeUnit.SECONDS);

        /*
         * 队列消费完成，回调通知消息完成
         */
        threadPoolExecutor1.scheduleAtFixedRate(()->{
            int a;
            if ((a = COMPLETE_BLOCKING_QUEUE.size())==0){
                return;
            }
            List<Long> msgIds = new ArrayList<>();
            for (int i = 0; i < a; i++) {
                Long l = COMPLETE_BLOCKING_QUEUE.poll();
                msgIds.add(l);
            }
            msgInfoMapper.updateBatch(msgIds,MsgStatusEnum.COMPLETED.getCode());
        },1,1, TimeUnit.SECONDS);
    }

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
//        MsgInfo msgInfo = msgInfoMapper.selectByPrimaryKey(msgInfoDTO.getMsgId());
//        msgInfo.setStatus(MsgStatusEnum.HAS_BEEN_SENT.getCode());
//        msgInfoMapper.updateBatch(Collections.singletonList(msgInfoDTO.getMsgId()),MsgStatusEnum.HAS_BEEN_SENT.getCode());
//        // 发送到消息队列
//        messageSender.publicQueueProcessing(JSON.toJSONString(msgInfo),msgInfoDTO.getTopic());
        SEND_BLOCKING_QUEUE.offer(msgInfoDTO.getMsgId());
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
//        List<MsgInfo> list = msgInfoMapper.selectBatch(batchMsgInfoDTO.getMsgIds());
//        // 发送到消息队列
//        list.forEach(each-> messageSender.publicQueueProcessing(JSON.toJSONString(each),each.getTopic()));
//        // 批次更新消息记录
//        msgInfoMapper.updateBatch(batchMsgInfoDTO.getMsgIds(),MsgStatusEnum.HAS_BEEN_SENT.getCode());
        batchMsgInfoDTO.getMsgIds().forEach(SEND_BLOCKING_QUEUE::offer);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse complete(MsgInfoDTO msgInfoDTO) {
        COMPLETE_BLOCKING_QUEUE.offer(msgInfoDTO.getMsgId());
//        msgInfoMapper.updateBatch(Collections.singletonList(msgInfoDTO.getMsgId()),MsgStatusEnum.COMPLETED.getCode());
        return BaseResponse.success();
    }

    @Override
    public BaseResponse<MsgInfo> selectMessage(long messageId) {
        return BaseResponse.successData(msgInfoMapper.selectByPrimaryKey(messageId));
    }

    private void business(List<Long> msgIds){
        List<MsgInfo> list = msgInfoMapper.selectBatch(msgIds);
        // 发送到消息队列
        list.forEach(each-> messageSender.publicQueueProcessing(JSON.toJSONString(each),each.getTopic()));
        // 批次更新消息记录
        msgInfoMapper.updateBatch(msgIds,MsgStatusEnum.HAS_BEEN_SENT.getCode());
    }
}
