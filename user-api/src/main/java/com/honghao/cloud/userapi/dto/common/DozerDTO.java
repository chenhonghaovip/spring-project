package com.honghao.cloud.userapi.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * @author chenhonghao
 * @date 2019-12-26 15:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DozerDTO {
    private Field field;

    private String name;
}
