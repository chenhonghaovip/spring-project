package com.honghao.cloud.userapi.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基础返回包装信息
 *
 * @author chenhonghao
 * @date 2019-07-17 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 6958518957432138343L;
    private boolean result;

    private int code;

    private T data;

    private String remark;

    public static <T> BaseResponse<T> successData(T data){
        return new BaseResponse<>(true,200,data,"请求成功");
    }

    public static BaseResponse success() {
        return new BaseResponse<>(true, 200, null, "请求成功");
    }

    public static <T> BaseResponse<T> success(String msg) {
        return new BaseResponse<>(true, 200, null, msg);
    }

    public static BaseResponse error(String msg) {
        return new BaseResponse<>(false, -1, null, msg);
    }

    public static BaseResponse error(String code, String remark) {
        return new BaseResponse<>(false, Integer.valueOf(code), null, remark);
    }

    public static BaseResponse error() {
        return new BaseResponse<>(false, -1, null, "请求失败");
    }

    public static BaseResponse error(BaseErrorInfoInterface data) {
        return new BaseResponse<>(false, Integer.valueOf(data.getResultCode()), null, data.getResultMsg());
    }


}
