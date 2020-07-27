package com.honghao.cloud.accountapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author CHH
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfo {
    private String shopId;

    private String shopName;

    private BigDecimal shopPrice;

    private String shopUrl;
}