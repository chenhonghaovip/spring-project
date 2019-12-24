package com.honghao.cloud.userapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudDict {
    @NotBlank
    private String id;
    @NotBlank
    private String parentid;
    @NotBlank
    private String path;
    @NotBlank(groups = Person.class)
    private String name;

    private String code;

    private Integer location;

    private Boolean haschildren;

    private String opby;

    private Integer opat;

    private Boolean delflag;

    public interface Person extends Default {

    }
}