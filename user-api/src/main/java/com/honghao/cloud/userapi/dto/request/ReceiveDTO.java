package com.honghao.cloud.userapi.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 集中送到店DTO
 *
 * @author chenhonghao
 * @date 2020-02-06 14:55
 */
public class ReceiveDTO implements Serializable {
    /**
     * 团队主键
     */
    private String unitId;

    /**
     * 骑士主键
     */
    private String kappId;

    /**
     * 骑士姓名
     */
    private String kappName;

    /**
     * 骑士手机
     */
    private  String kappPhone;

    /**
     * 运单号
     */
    private String wId;

    /**
     * 批次号
     */
    private String batchId;
    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 异常类型
     */
    private Integer abnormalType;

    /**
     * 异常原因
     */
    private String abnormalInfo;

    @Override
    public String toString() {
        return "CentralizedReceiveDTO{" +
                "unitId='" + unitId + '\'' +
                ", kappId='" + kappId + '\'' +
                ", kappName='" + kappName + '\'' +
                ", kappPhone='" + kappPhone + '\'' +
                ", wId='" + wId + '\'' +
                ", batchId='" + batchId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getKappId() {
        return kappId;
    }

    public void setKappId(String kappId) {
        this.kappId = kappId;
    }

    public String getKappName() {
        return kappName;
    }

    public void setKappName(String kappName) {
        this.kappName = kappName;
    }

    public String getKappPhone() {
        return kappPhone;
    }

    public void setKappPhone(String kappPhone) {
        this.kappPhone = kappPhone;
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
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

    public Integer getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(Integer abnormalType) {
        this.abnormalType = abnormalType;
    }

    public String getAbnormalInfo() {
        return abnormalInfo;
    }

    public void setAbnormalInfo(String abnormalInfo) {
        this.abnormalInfo = abnormalInfo;
    }
}
