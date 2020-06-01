package com.honghao.cloud.userapi.aspect;

import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author chenhonghao
 * @date 2020-05-30 21:39
 */
@Getter
@AllArgsConstructor
public enum DTOEnum {
    /**
     * first
     */
    first("com.honghao.cloud.userapi.domain.entity.WaybillBcList",new WaybillBcList()),

    second("com.honghao.cloud.userapi.aspect.DTOT",new DTOT())
    ;

    private String name;
    private Object object;

    public static List<String> CODES = Arrays.stream(DTOEnum.values()).map(DTOEnum::getName).collect(Collectors.toList());

    public static DTOEnum formCode(String name){
        Optional<DTOEnum> any = Arrays.stream(DTOEnum.values()).filter(each -> name.equals(each.getName())).findAny();
        return any.orElse(null);
    }
}
