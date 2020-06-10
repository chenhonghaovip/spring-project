package com.honghao.cloud.accountapi.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chenhonghao
 * @date 2019-09-18 14:45
 */
@Data
@AllArgsConstructor
public class CommonDTO {
    /**
     * 标识
     */
    private Integer code;
    /**
     * 内容
     */
    private String desc;
}
