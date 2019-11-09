package com.honghao.cloud.userapi.aspect;

import com.honghao.cloud.userapi.base.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 错误的兜底拦截，避免向外抛出
 * @author wyl
 *
 */
@ControllerAdvice
public class ControllerAdviceAspect {
	private static Logger logger = LoggerFactory.getLogger(ControllerAdviceAspect.class);
	
	@ExceptionHandler
	@ResponseBody
	public BaseResponse handle(Exception e) {
		logger.error("触发兜底拦截,{}", e.getMessage());
		if(e instanceof HttpMessageNotReadableException) {
			return BaseResponse.parameterChangeError();
		}else if (e instanceof MethodArgumentNotValidException){
			String filed = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().get(0).getField();
			String name = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().get(0).getDefaultMessage();
			return BaseResponse.error(filed + name);

		}
		return BaseResponse.error();
	}
}
