package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhonghao
 * @date 2019-12-03 17:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    private Trader trader;
    private int year;
    private int value;
}
