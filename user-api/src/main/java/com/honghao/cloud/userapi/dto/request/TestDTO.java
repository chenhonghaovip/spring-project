package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chenhonghao
 * @date 2019-11-11 19:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDTO implements Serializable {
    private String name;

    private Integer orderType;

    private BigDecimal totalPrice;

    private Double discount;
}
