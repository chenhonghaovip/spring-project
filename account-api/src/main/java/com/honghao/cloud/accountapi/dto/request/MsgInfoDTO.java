package com.honghao.cloud.accountapi.dto.request;

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

    private String content;

    private Integer status;

    private String topic;

    private Integer delay;

    private String appId;

    private String url;

    private String businessId;
}