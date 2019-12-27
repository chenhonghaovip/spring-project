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
public class WaybillBcListDetail {
    private String id;

    private String wId;

    private String unitId;

    private String kappId;

    private String kappName;

    private String kappPhone;

    private Integer kappAction;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Integer isSingle;

    private Date createDate;

    private Date createTime;

    private Integer deleteFlag;
}