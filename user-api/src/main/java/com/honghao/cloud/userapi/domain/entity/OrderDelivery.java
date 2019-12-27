package com.honghao.cloud.userapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author CHH
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDelivery {
    private String orderId;

    private String shopId;

    private String deliveryId;

    private String deliveryManId;

    private Integer isPay;

    private Double teamPay;

    private Double deliveryPay;

    private String knightName;

    private String knightPhone;

    private Integer deliveryStatus;

    private Double shareType;

    private Date receiveTime;

    private Date arrivalTime;

    private Date pickUpTime;

    private Date confirmReceiveTime;

    private Date createTime;

    private BigDecimal receiveLongitude;

    private BigDecimal receiveLatitude;

    private BigDecimal arrivalLongitude;

    private BigDecimal arrivalLatitude;

    private BigDecimal pickUpLongitude;

    private BigDecimal pickUpLatitude;

    private BigDecimal confirmLongitude;

    private BigDecimal confirmLatitude;
}