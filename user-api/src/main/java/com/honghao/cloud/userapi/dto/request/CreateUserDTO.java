package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 创建用户请求DTO
 *
 * @author chenhonghao
 * @date 2019-07-21 21:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO implements Serializable {
    private static final long serialVersionUID = -2845044232541422362L;
    /**
     * 用户id
     */
    private String userId;

}
