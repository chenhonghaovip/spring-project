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
public class CloudOrderOvertime {
    private String id;

    private String deliveryId;

    private String deliveryManId;

    private String phone;

    private String orderId;

    private String timeoutType;

    private Integer overTime;

    private Double fineAmount;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

    private Integer deleteFlag;
}