package com.honghao.cloud.userapi.domain.entity;

import java.util.Date;

public class WaybillBcListDetailAction {
    private String wId;

    private String unitId;

    private String kappId;

    private String kappName;

    private String kappPhone;

    private Integer kappAction;

    private Integer isSingle;

    private Date sSingleTime;

    private Date eSingleTime;

    private Date createTime;

    private Date updateTime;

    private Integer deleteFlag;

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

    public Integer getKappAction() {
        return kappAction;
    }

    public void setKappAction(Integer kappAction) {
        this.kappAction = kappAction;
    }

    public Integer getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(Integer isSingle) {
        this.isSingle = isSingle;
    }

    public Date getsSingleTime() {
        return sSingleTime;
    }

    public void setsSingleTime(Date sSingleTime) {
        this.sSingleTime = sSingleTime;
    }

    public Date geteSingleTime() {
        return eSingleTime;
    }

    public void seteSingleTime(Date eSingleTime) {
        this.eSingleTime = eSingleTime;
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