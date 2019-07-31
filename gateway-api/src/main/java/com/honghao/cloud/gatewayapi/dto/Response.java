package com.honghao.cloud.gatewayapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenhonghao
 * @date 2019-07-31 14:07
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Response implements Serializable {
    private static final long serialVersionUID = 2038799227930626737L;
    private Integer code;
    private String message;
    private String data;
}
