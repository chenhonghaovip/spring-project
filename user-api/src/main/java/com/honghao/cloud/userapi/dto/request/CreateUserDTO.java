package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

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
     * 是否使用优惠券
     */
    @NotBlank
    private String name;

    @NotEmpty
    /**
     * 用于对list集合内部对象进行校验
     */
    @Valid
    private List<Operator> list;
}
