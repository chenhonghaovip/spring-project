package com.honghao.cloud.userapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author CHH
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CloudDocument {
    private Integer id;

    private String documentName;

    private String documentType;

    private String documentAddress;

    private Date createTime;

    private String createBy;

    private Integer deleteFlag;
}