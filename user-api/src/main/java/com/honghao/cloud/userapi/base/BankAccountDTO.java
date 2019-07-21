package com.honghao.cloud.userapi.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通知结算
 *
 * @author chenhonghao
 * @date 2019-07-12 14:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountDTO {
    private String name;

    private Integer accountType;

    private String phone;

    private String bankCode;
}
