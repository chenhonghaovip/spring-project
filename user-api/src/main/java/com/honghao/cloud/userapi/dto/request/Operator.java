package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

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

    @NotNull
    private Integer operatorType;
    /**
     * 操作人员id,超管无
     */
    @NotNull(groups = First.class)
    private Long operatorId;
    /**
     * 操作人员id
     */
    @NotBlank(groups = First.class)
    private String operatorNo;

    /**
     * 操作人员名字
     */
    @NotBlank(groups = Second.class)
    private String operatorName;
    /**
     * 操作渠道商
     */
    @NotBlank(groups = Second.class)
    private String agentNo;

    public Operator(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public interface First extends Default {
    }

    public interface Second extends Default {
    }
}
