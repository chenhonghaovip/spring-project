package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求操作人信息
 *
 * @author luxinghui
 * @date 2019-05-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operator {

    /**
     * 操作人类型
     *
     * @see
     */
    private Integer operatorType;
    /**
     * 操作人员id,超管无
     */
    private Long operatorId;
    /**
     * 操作人员id
     */
    private String operatorNo;

    /**
     * 操作人员名字
     */
    private String operatorName;
    /**
     * 操作渠道商
     */
    private String agentNo;

}
