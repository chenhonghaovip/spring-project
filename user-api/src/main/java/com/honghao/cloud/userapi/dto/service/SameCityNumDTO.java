package com.honghao.cloud.userapi.dto.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhonghao
 * @date 2019-12-26 10:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SameCityNumDTO {
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 订单数量
     */
    private Integer num;
}
