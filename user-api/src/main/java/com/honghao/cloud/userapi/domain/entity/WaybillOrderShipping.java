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
public class WaybillOrderShipping {
    private String wId;

    private BigDecimal distance;

    private BigDecimal basics;

    private BigDecimal overhang;

    private BigDecimal fastigium;

    private BigDecimal weather;

    private BigDecimal platformSubsidy;

    private BigDecimal platformIncome;

    private BigDecimal discount;

    private String couponUserId;

    private Integer couponType;

    private Date createTime;

    private Integer deleteFlag;
}