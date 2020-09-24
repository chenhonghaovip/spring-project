package com.honghao.cloud.userapi.dto.request;

import lombok.Data;

/**
 * @author CHH
 */
@Data
public class MsgInfoDTO {
    private Long msgId;

    private String content;

    private Integer status;

    private String topic;

    private Integer delay;

    private String appId;

    private String url;
}