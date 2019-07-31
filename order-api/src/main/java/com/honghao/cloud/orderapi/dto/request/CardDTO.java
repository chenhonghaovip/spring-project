package com.honghao.cloud.orderapi.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 卡片请求DTO
 *
 * @author chenhonghao
 * @date 2019-07-31 13:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDTO implements Serializable {

    @JSONField(name = "isBindCard")
    private boolean isBindCard;
    @JSONField(name = "isSign")
    private boolean isSign;
    @JSONField(name = "cardInfo")
    private CardInfoBean cardInfo;
    @JSONField(name = "accountInfo")
    private AccountInfoBean accountInfo;
    @JSONField(name = "isOpen")
    private boolean isOpen;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CardInfoBean {
        @JSONField(name = "cardNo")
        private String cardNo;
        @JSONField(name = "pinYin")
        private String pinYin;
        @JSONField(name = "isSign")
        private boolean isSign;
        @JSONField(name = "cardName")
        private String cardName;
        @JSONField(name = "accBankNo")
        private String accBankNo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AccountInfoBean {
        @JSONField(name = "phone")
        private String phone;
        @JSONField(name = "identityName")
        private String identityName;
        @JSONField(name = "identityCard")
        private String identityCard;
    }
}
