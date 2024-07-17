package com.honghao.cloud.middle.anno.common;

import lombok.Getter;

@Getter
public enum ProcessStatusEnum {

    ;

    private String firstTableCode;

    private String statusField;

    private String frontStatus;

    private String realStatus;

    ProcessStatusEnum(String firstTableCode, String statusField, String frontStatus, String realStatus) {
        this.firstTableCode = firstTableCode;
        this.statusField = statusField;
        this.frontStatus = frontStatus;
        this.realStatus = realStatus;
    }
}
