package com.honghao.cloud.userapi.dto.service;

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
public class WaybillBcListDTO {
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

    private Byte isTimely;

    private Date etStartTime;

    private Date etArriveTime;

    private BigDecimal cargoWeight;

    private String cargoType;

    private Byte orderStatus;

    private Byte orderPayment;

    private Byte orderPayStatus;

    private BigDecimal deliveryFee;

    private BigDecimal totalPrices;

    private String orderRemark;

    private Byte orderSource;

    private Byte orderType;

    private Byte singleWay;

    private Byte deliveryMode;

    private Date orderTime;

    private Date createDate;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private Byte deleteFlag;
}