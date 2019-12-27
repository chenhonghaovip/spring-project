package com.honghao.cloud.userapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author CHH
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}