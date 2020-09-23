package com.honghao.cloud.accountapi.dto.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author CHH
 */
@Data
@Builder
public class MsgInfoDTO {
    private Long msgId;

    private String content;

    private Integer status;

    private String topic;

    private Integer delay;


    private String appId;

    private String url;

    /**
     * 消费者服务名
     */
    private String consumerAppId;
    /**
     * 消费者回调查询路径
     */
    private String consumerUrl;

    private String businessId;
}