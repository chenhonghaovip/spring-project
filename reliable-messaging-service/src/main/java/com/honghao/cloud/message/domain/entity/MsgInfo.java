package com.honghao.cloud.message.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author chenhonghao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgInfo {
    private Long msgId;

    private String businessId;

    private String content;

    private Integer status;

    private String appId;

    private String url;

    private Integer retryTime;

    private String topic;

    private Integer delay;

    private LocalDateTime createTime;
}