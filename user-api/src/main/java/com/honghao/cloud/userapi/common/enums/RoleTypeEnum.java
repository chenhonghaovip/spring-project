package com.honghao.cloud.userapi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色类型
 *
 * @author chenhonghao
 * @date 2019-07-18 13:13
 */
@AllArgsConstructor
@Getter
public enum RoleTypeEnum {
    /**
     * 超级管理员
     */
    SUPER_MANAGER(1, "超级管理员");

    /**
     * 获取code集合
     */
    private static final List<Integer> CODES = Arrays.stream(RoleTypeEnum.values()).map(RoleTypeEnum::getCode).collect(Collectors.toList());
    private Integer code;
    private String desc;

    /**
     * 获取对应中文含义
     *
     * @param code 编号
     * @return 枚举值
     */
    public static RoleTypeEnum formCode(@Min(1) @Max(3) Integer code) {
        if (!CODES.contains(code)) {
            throw new RuntimeException("类型转换异常");
        }
        return Arrays.stream(RoleTypeEnum.values()).filter(each -> code.equals(each.getCode())).findFirst().orElse(null);
    }
}
