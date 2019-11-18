package com.honghao.cloud.userapi.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId == null ? null : deliveryId.trim();
    }

    public String getDeliveryManId() {
        return deliveryManId;
    }

    public void setDeliveryManId(String deliveryManId) {
        this.deliveryManId = deliveryManId == null ? null : deliveryManId.trim();
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Double getTeamPay() {
        return teamPay;
    }

    public void setTeamPay(Double teamPay) {
        this.teamPay = teamPay;
    }

    public Double getDeliveryPay() {
        return deliveryPay;
    }

    public void setDeliveryPay(Double deliveryPay) {
        this.deliveryPay = deliveryPay;
    }

    public String getKnightName() {
        return knightName;
    }

    public void setKnightName(String knightName) {
        this.knightName = knightName == null ? null : knightName.trim();
    }

    public String getKnightPhone() {
        return knightPhone;
    }

    public void setKnightPhone(String knightPhone) {
        this.knightPhone = knightPhone == null ? null : knightPhone.trim();
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Double getShareType() {
        return shareType;
    }

    public void setShareType(Double shareType) {
        this.shareType = shareType;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Date getConfirmReceiveTime() {
        return confirmReceiveTime;
    }

    public void setConfirmReceiveTime(Date confirmReceiveTime) {
        this.confirmReceiveTime = confirmReceiveTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getReceiveLongitude() {
        return receiveLongitude;
    }

    public void setReceiveLongitude(BigDecimal receiveLongitude) {
        this.receiveLongitude = receiveLongitude;
    }

    public BigDecimal getReceiveLatitude() {
        return receiveLatitude;
    }

    public void setReceiveLatitude(BigDecimal receiveLatitude) {
        this.receiveLatitude = receiveLatitude;
    }

    public BigDecimal getArrivalLongitude() {
        return arrivalLongitude;
    }

    public void setArrivalLongitude(BigDecimal arrivalLongitude) {
        this.arrivalLongitude = arrivalLongitude;
    }

    public BigDecimal getArrivalLatitude() {
        return arrivalLatitude;
    }

    public void setArrivalLatitude(BigDecimal arrivalLatitude) {
        this.arrivalLatitude = arrivalLatitude;
    }

    public BigDecimal getPickUpLongitude() {
        return pickUpLongitude;
    }

    public void setPickUpLongitude(BigDecimal pickUpLongitude) {
        this.pickUpLongitude = pickUpLongitude;
    }

    public BigDecimal getPickUpLatitude() {
        return pickUpLatitude;
    }

    public void setPickUpLatitude(BigDecimal pickUpLatitude) {
        this.pickUpLatitude = pickUpLatitude;
    }

    public BigDecimal getConfirmLongitude() {
        return confirmLongitude;
    }

    public void setConfirmLongitude(BigDecimal confirmLongitude) {
        this.confirmLongitude = confirmLongitude;
    }

    public BigDecimal getConfirmLatitude() {
        return confirmLatitude;
    }

    public void setConfirmLatitude(BigDecimal confirmLatitude) {
        this.confirmLatitude = confirmLatitude;
    }
}