package com.honghao.cloud.userapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author CHH
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaybillBcList {
    @NotBlank(message = "wId不能为空")
    private String wId;

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

    private Integer isTimely;

    private Date etStartTime;

    private Date etArriveTime;

    private BigDecimal cargoWeight;

    private BigDecimal cargoAmount;

    private String cargoType;

    private Integer orderStatus;

    private Integer orderPayment;

    private Integer orderPayStatus;

    private BigDecimal deliveryFee;

    private BigDecimal totalPrices;

    private BigDecimal expectPrices;

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

    private Integer isOverdistance;

    private Integer isOvertime;

}