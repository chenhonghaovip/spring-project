package com.honghao.cloud.userapi.domain.entity;

import java.util.Date;

public class WaybillMerchantsArbitrament {
    private String orderId;

    private String complainId;

    private String complainPhone;

    private String complainPicture;

    private String complainRemark;

    private Date complainTime;

    private String appealTeamId;

    private String appealId;

    private String appealPhone;

    private String appealPicture;

    private String appealRemark;

    private Date appealTime;

    private String complainType;

    private Double fines;

    private Double actualFines;

    private Double damage;

    private Double actualDamage;

    private String arbitrationRemark;

    private String arbitrationObject;

    private Integer arbitrationResult;

    private Date arbitrationTime;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

    private Integer deleteFlag;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getComplainId() {
        return complainId;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId == null ? null : complainId.trim();
    }

    public String getComplainPhone() {
        return complainPhone;
    }

    public void setComplainPhone(String complainPhone) {
        this.complainPhone = complainPhone == null ? null : complainPhone.trim();
    }

    public String getComplainPicture() {
        return complainPicture;
    }

    public void setComplainPicture(String complainPicture) {
        this.complainPicture = complainPicture == null ? null : complainPicture.trim();
    }

    public String getComplainRemark() {
        return complainRemark;
    }

    public void setComplainRemark(String complainRemark) {
        this.complainRemark = complainRemark == null ? null : complainRemark.trim();
    }

    public Date getComplainTime() {
        return complainTime;
    }

    public void setComplainTime(Date complainTime) {
        this.complainTime = complainTime;
    }

    public String getAppealTeamId() {
        return appealTeamId;
    }

    public void setAppealTeamId(String appealTeamId) {
        this.appealTeamId = appealTeamId == null ? null : appealTeamId.trim();
    }

    public String getAppealId() {
        return appealId;
    }

    public void setAppealId(String appealId) {
        this.appealId = appealId == null ? null : appealId.trim();
    }

    public String getAppealPhone() {
        return appealPhone;
    }

    public void setAppealPhone(String appealPhone) {
        this.appealPhone = appealPhone == null ? null : appealPhone.trim();
    }

    public String getAppealPicture() {
        return appealPicture;
    }

    public void setAppealPicture(String appealPicture) {
        this.appealPicture = appealPicture == null ? null : appealPicture.trim();
    }

    public String getAppealRemark() {
        return appealRemark;
    }

    public void setAppealRemark(String appealRemark) {
        this.appealRemark = appealRemark == null ? null : appealRemark.trim();
    }

    public Date getAppealTime() {
        return appealTime;
    }

    public void setAppealTime(Date appealTime) {
        this.appealTime = appealTime;
    }

    public String getComplainType() {
        return complainType;
    }

    public void setComplainType(String complainType) {
        this.complainType = complainType == null ? null : complainType.trim();
    }

    public Double getFines() {
        return fines;
    }

    public void setFines(Double fines) {
        this.fines = fines;
    }

    public Double getActualFines() {
        return actualFines;
    }

    public void setActualFines(Double actualFines) {
        this.actualFines = actualFines;
    }

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) {
        this.damage = damage;
    }

    public Double getActualDamage() {
        return actualDamage;
    }

    public void setActualDamage(Double actualDamage) {
        this.actualDamage = actualDamage;
    }

    public String getArbitrationRemark() {
        return arbitrationRemark;
    }

    public void setArbitrationRemark(String arbitrationRemark) {
        this.arbitrationRemark = arbitrationRemark == null ? null : arbitrationRemark.trim();
    }

    public String getArbitrationObject() {
        return arbitrationObject;
    }

    public void setArbitrationObject(String arbitrationObject) {
        this.arbitrationObject = arbitrationObject == null ? null : arbitrationObject.trim();
    }

    public Integer getArbitrationResult() {
        return arbitrationResult;
    }

    public void setArbitrationResult(Integer arbitrationResult) {
        this.arbitrationResult = arbitrationResult;
    }

    public Date getArbitrationTime() {
        return arbitrationTime;
    }

    public void setArbitrationTime(Date arbitrationTime) {
        this.arbitrationTime = arbitrationTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}