package com.honghao.cloud.userapi.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

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

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId == null ? null : wId.trim();
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getBasics() {
        return basics;
    }

    public void setBasics(BigDecimal basics) {
        this.basics = basics;
    }

    public BigDecimal getOverhang() {
        return overhang;
    }

    public void setOverhang(BigDecimal overhang) {
        this.overhang = overhang;
    }

    public BigDecimal getFastigium() {
        return fastigium;
    }

    public void setFastigium(BigDecimal fastigium) {
        this.fastigium = fastigium;
    }

    public BigDecimal getWeather() {
        return weather;
    }

    public void setWeather(BigDecimal weather) {
        this.weather = weather;
    }

    public BigDecimal getPlatformSubsidy() {
        return platformSubsidy;
    }

    public void setPlatformSubsidy(BigDecimal platformSubsidy) {
        this.platformSubsidy = platformSubsidy;
    }

    public BigDecimal getPlatformIncome() {
        return platformIncome;
    }

    public void setPlatformIncome(BigDecimal platformIncome) {
        this.platformIncome = platformIncome;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(String couponUserId) {
        this.couponUserId = couponUserId == null ? null : couponUserId.trim();
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}