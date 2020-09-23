package com.honghao.cloud.accountapi.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class WaybillBcList {
    private String batchId;

    private String serialNumber;

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

    private LocalDateTime etStartTime;

    private Date etArriveTime;

    private BigDecimal cargoWeight;

    private BigDecimal cargoAmount;

    private String cargoType;

    private Byte orderStatus;

    private Byte orderPayment;

    private Byte orderPayStatus;

    private BigDecimal deliveryFee;

    private BigDecimal totalPrices;

    private BigDecimal expectPrices;

    private String orderRemark;

    private Byte orderSource;

    private Integer orderType;

    private Byte singleWay;

    private Byte deliveryMode;

    private Date orderTime;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private Byte deleteFlag;

    private Byte isOverdistance;

    private Byte isOvertime;
    private String wId;

    private Date createDate;
}