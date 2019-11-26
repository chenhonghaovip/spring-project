package com.honghao.cloud.userapi.domain.entity;

import java.util.Date;

public class WaybillBcOrderChange {
    private String id;

    private String batchId;

    private String unitId;

    private String kappId;

    private String kappName;

    private String kappPhone;

    private Byte isDel;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
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

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
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
}