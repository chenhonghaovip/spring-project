package com.honghao.cloud.basic.common.exceptions;

/**
 * @author chenhonghao12
 * @version 1.0
 */
public class BusinessException extends RuntimeException {
    private static final Integer DEFAULT_ERROR_CODE = -200;


    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;

    public BusinessException() {
        super();
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = errorMsg;
    }

    public BusinessException(CommonException commonException) {
        super(commonException.getErrorMsg());
        this.errorCode = commonException.getErrorCode();
        this.errorMsg = commonException.getErrorMsg();
    }
}
