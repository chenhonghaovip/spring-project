package com.honghao.cloud.userapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author CHH
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaybillBcListDetailAction {
    private String wId;

    private String unitId;

    private String kappId;

    private String kappName;

    private String kappPhone;

    private Integer kappAction;

    private Integer isSingle;

    private Date sSingleTime;

    private Date eSingleTime;

    private Date createTime;

    private Date updateTime;

    private Integer deleteFlag;

}