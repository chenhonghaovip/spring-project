package com.honghao.cloud.accountapi.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

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

    private Date etStartTime;

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

    private Byte orderType;

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

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId == null ? null : wId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId == null ? null : shipId.trim();
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName == null ? null : sendName.trim();
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone == null ? null : sendPhone.trim();
    }

    public String getSendProvince() {
        return sendProvince;
    }

    public void setSendProvince(String sendProvince) {
        this.sendProvince = sendProvince == null ? null : sendProvince.trim();
    }

    public String getSendCity() {
        return sendCity;
    }

    public void setSendCity(String sendCity) {
        this.sendCity = sendCity == null ? null : sendCity.trim();
    }

    public String getSendCounty() {
        return sendCounty;
    }

    public void setSendCounty(String sendCounty) {
        this.sendCounty = sendCounty == null ? null : sendCounty.trim();
    }

    public String getSendAdcode() {
        return sendAdcode;
    }

    public void setSendAdcode(String sendAdcode) {
        this.sendAdcode = sendAdcode == null ? null : sendAdcode.trim();
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress == null ? null : sendAddress.trim();
    }

    public BigDecimal getSendLongitude() {
        return sendLongitude;
    }

    public void setSendLongitude(BigDecimal sendLongitude) {
        this.sendLongitude = sendLongitude;
    }

    public BigDecimal getSendLatitude() {
        return sendLatitude;
    }

    public void setSendLatitude(BigDecimal sendLatitude) {
        this.sendLatitude = sendLatitude;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName == null ? null : receiveName.trim();
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone == null ? null : receivePhone.trim();
    }

    public String getReceiveProvince() {
        return receiveProvince;
    }

    public void setReceiveProvince(String receiveProvince) {
        this.receiveProvince = receiveProvince == null ? null : receiveProvince.trim();
    }

    public String getReceiveCounty() {
        return receiveCounty;
    }

    public void setReceiveCounty(String receiveCounty) {
        this.receiveCounty = receiveCounty == null ? null : receiveCounty.trim();
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity == null ? null : receiveCity.trim();
    }

    public String getReceiveAdcode() {
        return receiveAdcode;
    }

    public void setReceiveAdcode(String receiveAdcode) {
        this.receiveAdcode = receiveAdcode == null ? null : receiveAdcode.trim();
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress == null ? null : receiveAddress.trim();
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

    public Byte getIsTimely() {
        return isTimely;
    }

    public void setIsTimely(Byte isTimely) {
        this.isTimely = isTimely;
    }

    public Date getEtStartTime() {
        return etStartTime;
    }

    public void setEtStartTime(Date etStartTime) {
        this.etStartTime = etStartTime;
    }

    public Date getEtArriveTime() {
        return etArriveTime;
    }

    public void setEtArriveTime(Date etArriveTime) {
        this.etArriveTime = etArriveTime;
    }

    public BigDecimal getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(BigDecimal cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public BigDecimal getCargoAmount() {
        return cargoAmount;
    }

    public void setCargoAmount(BigDecimal cargoAmount) {
        this.cargoAmount = cargoAmount;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType == null ? null : cargoType.trim();
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Byte getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(Byte orderPayment) {
        this.orderPayment = orderPayment;
    }

    public Byte getOrderPayStatus() {
        return orderPayStatus;
    }

    public void setOrderPayStatus(Byte orderPayStatus) {
        this.orderPayStatus = orderPayStatus;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(BigDecimal totalPrices) {
        this.totalPrices = totalPrices;
    }

    public BigDecimal getExpectPrices() {
        return expectPrices;
    }

    public void setExpectPrices(BigDecimal expectPrices) {
        this.expectPrices = expectPrices;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark == null ? null : orderRemark.trim();
    }

    public Byte getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Byte orderSource) {
        this.orderSource = orderSource;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Byte getSingleWay() {
        return singleWay;
    }

    public void setSingleWay(Byte singleWay) {
        this.singleWay = singleWay;
    }

    public Byte getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(Byte deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Byte getIsOverdistance() {
        return isOverdistance;
    }

    public void setIsOverdistance(Byte isOverdistance) {
        this.isOverdistance = isOverdistance;
    }

    public Byte getIsOvertime() {
        return isOvertime;
    }

    public void setIsOvertime(Byte isOvertime) {
        this.isOvertime = isOvertime;
    }
}