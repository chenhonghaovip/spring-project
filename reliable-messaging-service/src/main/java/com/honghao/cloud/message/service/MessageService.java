package com.honghao.cloud.message.service;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.message.domain.entity.MsgInfo;
import com.honghao.cloud.message.dto.MsgInfoDTO;

/**
 * 消息服务
 *
 * @author chenhonghao
 * @date 2020-09-24 15:39
 */
public interface MessageService {

    /**
     * 预发布消息
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    BaseResponse saveMessage(MsgInfoDTO msgInfoDTO);

    /**
     * 发送消息到队列
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    BaseResponse send(MsgInfoDTO msgInfoDTO);

    /**
     * 消息处理完成
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    BaseResponse complete(MsgInfoDTO msgInfoDTO);

    /**
     * 查询消息状态
     * @param messageId messageId
     * @return BaseResponse
     */
    BaseResponse<MsgInfo> selectMessage(long messageId);
}
