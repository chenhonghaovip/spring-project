package com.honghao.cloud.userapi.domain.entity;

import java.util.Date;

public class CloudDeliveryMan {
    private String deliveryManId;

    private String deliveryId;

    private Long headId;

    private String name;

    private Byte sex;

    private String phone;

    private String password;

    private Integer province;

    private Integer city;

    private Integer country;

    private String address;

    private String idCard;

    private Long identityId;

    private Byte workStatus;

    private Byte applyStatus;

    private Byte qualificationStatus;

    private Byte regFrom;

    private String otherRegAccount;

    private Byte loginFrom;

    private String bindWx;

    private String inviteCode;

    private Date joinTime;

    private Byte serviceStatus;

    private String createBy;

    private Long createTime;

    private String updateBy;

    private Long updateTime;

    private Boolean deleteFlag;

    public String getDeliveryManId() {
        return deliveryManId;
    }

    public void setDeliveryManId(String deliveryManId) {
        this.deliveryManId = deliveryManId == null ? null : deliveryManId.trim();
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId == null ? null : deliveryId.trim();
    }

    public Long getHeadId() {
        return headId;
    }

    public void setHeadId(Long headId) {
        this.headId = headId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Long getIdentityId() {
        return identityId;
    }

    public void setIdentityId(Long identityId) {
        this.identityId = identityId;
    }

    public Byte getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Byte workStatus) {
        this.workStatus = workStatus;
    }

    public Byte getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Byte applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Byte getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(Byte qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }

    public Byte getRegFrom() {
        return regFrom;
    }

    public void setRegFrom(Byte regFrom) {
        this.regFrom = regFrom;
    }

    public String getOtherRegAccount() {
        return otherRegAccount;
    }

    public void setOtherRegAccount(String otherRegAccount) {
        this.otherRegAccount = otherRegAccount == null ? null : otherRegAccount.trim();
    }

    public Byte getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(Byte loginFrom) {
        this.loginFrom = loginFrom;
    }

    public String getBindWx() {
        return bindWx;
    }

    public void setBindWx(String bindWx) {
        this.bindWx = bindWx == null ? null : bindWx.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Byte getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Byte serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}