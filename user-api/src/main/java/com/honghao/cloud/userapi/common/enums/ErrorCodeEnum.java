package com.honghao.cloud.userapi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误信息enum
 *
 * @author luxinghui
 * @date 2019/05/08
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    /**
     * 成功
     */
    SUCCESS("CPM000000", "success"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR("CPM100000", "系统繁忙，请稍后充实"),

    /**
     * 参数错误
     */
    PARAM_ERROR("CPM100001", "参数错误"),

    /**
     * 熔断异常
     */
    FAIL_BACK_ERROR("CPM100002", "接口熔断异常"),

    /**
     * Json解析错误
     */
    JSON_PARSER_ERROR("CPM100003", "Json解析错误"),
    PARSE_ERROR("CPM100004", "参数无法解析"),

    PARAM_TYPE_ERROR("CPM1000005","参数类型错误"),

    ENTITY_NOT_FOUND("CPM100100", "未找到实体类"),
    REQUEST_PARAM_ERROR("CPM100002", "参数错误"),

    POST_NAME_NULL_ERROR("CPM100003", "请填写岗位名称"),
    POST_NAME_LENGTH_ERROR("CPM100004", "岗位名称请填写10字以内"),
    POST_NAME_REPEAT_ERROR("CPM100005", "和已有岗位名称重复"),
    POST_TYPE_NULL_ERROR("CPM100006", "请选择岗位类型"),
    REMARK_LENGTH_ERROR("CPM100007", "岗位备注请填写30字以内"),
    MEMBER_NOT_EXIST_ERROR("CPM100008", "当前用户不存在或已冻结"),
    FEIGN_CLIENT_ERROR("CPM100009", "接口调用异常"),
    FEIGN_DEAL_ERROR("CPM100010", "处理失败"),
    NO_PERMISSION_ERROR("CPM100011", "当前操作无权限"),
    IMPORT_NUMBER_ERROR("CPM100012", "每次导入不能大于200人"),
    LEVEL_ID_INFO_FAILED("CPM100013", "1号员工不可修改代理级别"),

    EXCEL_ERROR("CPM100014", "Excel解析失败"),
    ;
    private String code;
    private String message;
}
