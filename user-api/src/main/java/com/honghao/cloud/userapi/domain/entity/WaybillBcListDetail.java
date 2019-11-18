package com.honghao.cloud.userapi.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class WaybillBcListDetail {
    private String id;

    private String wId;

    private String unitId;

    private String kappId;

    private String kappName;

    private String kappPhone;

    private Byte kappAction;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Byte isSingle;

    private Date createDate;

    private Date createTime;

    private Byte deleteFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId == null ? null : wId.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getKappId() {
        return kappId;
    }

    public void setKappId(String kappId) {
        this.kappId = kappId == null ? null : kappId.trim();
    }

    public String getKappName() {
        return kappName;
    }

    public void setKappName(String kappName) {
        this.kappName = kappName == null ? null : kappName.trim();
    }

    public String getKappPhone() {
        return kappPhone;
    }

    public void setKappPhone(String kappPhone) {
        this.kappPhone = kappPhone == null ? null : kappPhone.trim();
    }

    public Byte getKappAction() {
        return kappAction;
    }

    public void setKappAction(Byte kappAction) {
        this.kappAction = kappAction;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Byte getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(Byte isSingle) {
        this.isSingle = isSingle;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}