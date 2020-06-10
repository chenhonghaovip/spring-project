package com.honghao.cloud.accountapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author CHH
 * @date 2019-7-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaybillBcList {
    private String wId;

    private String batchId;

    private String userId;

    private String shipId;

    private String sendName;

    private String sendPhone;

    private String sendProvince;

    private String sendCity;

    private String sendCounty;

    private String sendAdcode;

    private String sendAddress;

    private BigDecimal sendLongitude;

    private BigDecimal sendLatitude;

    private String receiveName;

    private String receivePhone;

    private String receiveProvince;

    private String receiveCounty;

    private String receiveCity;

    private String receiveAdcode;

    private String receiveAddress;

    private BigDecimal receiveLongitude;

    private BigDecimal receiveLatitude;

    private Integer isTimely;

    private Date etStartTime;

    private Date etArriveTime;

    private BigDecimal cargoWeight;

    private String cargoType;

    private Integer orderStatus;

    private Integer orderPayment;

    private Integer orderPayStatus;

    private BigDecimal deliveryFee;

    private BigDecimal totalPrices;

    private String orderRemark;

    private Integer orderSource;

    private Integer orderType;

    private Integer singleWay;

    private Integer deliveryMode;

    private Date orderTime;

    private Date createDate;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private Integer deleteFlag;
}