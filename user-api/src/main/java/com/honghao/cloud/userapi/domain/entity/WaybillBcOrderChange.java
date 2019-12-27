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
public class WaybillBcOrderChange {
    private String id;

    private String batchId;

    private String unitId;

    private String kappId;

    private String kappName;

    private String kappPhone;

    private Byte isDel;

    private Date createTime;

    private Date updateTime;
}