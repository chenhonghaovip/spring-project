package com.honghao.cloud.userapi.base;

/**
 * 通知结算
 *
 * @author chenhonghao
 * @date 2019-07-12 14:19
 */
public class BankAccountDTO {
    private String name;

    private Integer accountType;

    private String phone;

    private String bankCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
