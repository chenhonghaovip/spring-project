package com.honghao.cloud.orderapi.dto.common;

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

    private String businessId;
}