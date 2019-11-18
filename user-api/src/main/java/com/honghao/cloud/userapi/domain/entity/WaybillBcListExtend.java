package com.honghao.cloud.userapi.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class WaybillBcListExtend {
    private String wId;

    private String cargoInfo;

    private Integer buyingPatterns;

    private BigDecimal predictPrice;

    private String smallTicketPictureUrl;

    private String productPictureUrl;

    private Date createTime;

    private Date updateTime;

    private Integer deleteFlag;

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId == null ? null : wId.trim();
    }

    public String getCargoInfo() {
        return cargoInfo;
    }

    public void setCargoInfo(String cargoInfo) {
        this.cargoInfo = cargoInfo == null ? null : cargoInfo.trim();
    }

    public Integer getBuyingPatterns() {
        return buyingPatterns;
    }

    public void setBuyingPatterns(Integer buyingPatterns) {
        this.buyingPatterns = buyingPatterns;
    }

    public BigDecimal getPredictPrice() {
        return predictPrice;
    }

    public void setPredictPrice(BigDecimal predictPrice) {
        this.predictPrice = predictPrice;
    }

    public String getSmallTicketPictureUrl() {
        return smallTicketPictureUrl;
    }

    public void setSmallTicketPictureUrl(String smallTicketPictureUrl) {
        this.smallTicketPictureUrl = smallTicketPictureUrl == null ? null : smallTicketPictureUrl.trim();
    }

    public String getProductPictureUrl() {
        return productPictureUrl;
    }

    public void setProductPictureUrl(String productPictureUrl) {
        this.productPictureUrl = productPictureUrl == null ? null : productPictureUrl.trim();
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