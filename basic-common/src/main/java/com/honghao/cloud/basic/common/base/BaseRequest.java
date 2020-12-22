package com.honghao.cloud.basic.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基础请求包装信息
 *
 * @author chenhonghao
 * @date 2019-07-17 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequest implements Serializable {
    private static final long serialVersionUID = 6958518957432138343L;
    private String data;
}
