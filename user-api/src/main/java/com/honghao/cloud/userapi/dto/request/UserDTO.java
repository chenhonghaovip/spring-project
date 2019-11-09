package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author chenhonghao
 * @date 2019-11-02 16:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    private String name;
    @NotNull
    private Integer age;
    @NotBlank
    private String phone;
}
