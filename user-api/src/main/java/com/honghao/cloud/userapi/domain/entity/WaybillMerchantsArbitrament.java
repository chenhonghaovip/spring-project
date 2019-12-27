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
public class WaybillMerchantsArbitrament {
    private String orderId;

    private String complainId;

    private String complainPhone;

    private String complainPicture;

    private String complainRemark;

    private Date complainTime;

    private String appealTeamId;

    private String appealId;

    private String appealPhone;

    private String appealPicture;

    private String appealRemark;

    private Date appealTime;

    private String complainType;

    private Double fines;

    private Double actualFines;

    private Double damage;

    private Double actualDamage;

    private String arbitrationRemark;

    private String arbitrationObject;

    private Integer arbitrationResult;

    private Date arbitrationTime;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

    private Integer deleteFlag;
}