package com.honghao.cloud.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author CHH
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgInfoDTO {
    private Long msgId;

    private String businessId;

    private String content;

    private Integer status;

    private String appId;

    private String url;

    private String topic;

    private Integer retryTime;

    private Integer delay;


}