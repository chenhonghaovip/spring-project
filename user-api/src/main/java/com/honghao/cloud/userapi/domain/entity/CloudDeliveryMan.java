package com.honghao.cloud.userapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CloudDeliveryMan {
    private String deliveryManId;

    private String deliveryId;

    private Long headId;

    private String name;

    private Integer sex;

    private String phone;

    private String password;

    private Integer province;

    private Integer city;

    private Integer country;

    private String address;

    private String idCard;

    private Long identityId;

    private Integer workStatus;

    private Integer applyStatus;

    private Integer qualificationStatus;

    private Integer regFrom;

    private String otherRegAccount;

    private Integer loginFrom;

    private String bindWx;

    private String inviteCode;

    private Date joinTime;

    private Integer serviceStatus;

    private String createBy;

    private Long createTime;

    private String updateBy;

    private Long updateTime;

    private Integer deleteFlag;
}