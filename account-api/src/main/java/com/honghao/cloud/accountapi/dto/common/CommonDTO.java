package com.honghao.cloud.accountapi.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhonghao
 * @date 2019-09-18 14:45
 */
@Data
@Builder
@NoArgsConstructor
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
