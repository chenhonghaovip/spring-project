package com.honghao.cloud.userapi.base;

import com.honghao.cloud.userapi.common.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 错误的兜底拦截，避免向外抛出
 * @author wyl
 *
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdviceAspect {

	/**
	 * NoHandlerFoundException 404 异常处理
	 * @param e 异常信息
	 * @return BaseResponse
	 */
	@ExceptionHandler(value = NoHandlerFoundException.class)
	public BaseResponse handlerNoHandlerFoundException(NoHandlerFoundException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
	 * @param e 异常信息
	 * @return BaseResponse
	 */
	@ExceptionHandler(BindException.class)
	public BaseResponse bindExceptionHandler(BindException e) {
		String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
		return BaseResponse.error(message);
	}

	/**
	 * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
	 * @param e 异常信息
	 * @return BaseResponse
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public BaseResponse constraintViolationExceptionHandler(ConstraintViolationException e) {
		String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
		return BaseResponse.error(message);
	}

	/**
	 * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
	 * @param e 异常信息
	 * @return BaseResponse
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse("参数异常");
		return BaseResponse.error(message);
	}
	/**
	 * 空指针异常
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler(value = NullPointerException.class)
	public BaseResponse exceptionHandler(NullPointerException e){
		log.error("发生空指针异常！原因是:{}", e.getMessage());
		return BaseResponse.error(e.getMessage());
	}

	/**
	 * 类型转换异常
	 * @param e 异常信息
	 * @return BaseResponse
	 */
	@ExceptionHandler(ClassCastException.class)
	public BaseResponse classCastExceptionHandler(ClassCastException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 运行时异常
	 * @param e 异常信息
	 * @return BaseResponse
	 */
	@ExceptionHandler(RuntimeException.class)
	public BaseResponse runtimeExceptionHandler(RuntimeException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * IO异常
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler(IOException.class)
	public BaseResponse ioExceptionhandler(IOException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 未知方法异常
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler(NoSuchMethodException.class)
	public BaseResponse noSuchMethodExceptionHandler(NoSuchMethodException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 数组越界异常
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public BaseResponse indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 400错误
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public BaseResponse requestNotReadable(HttpMessageNotReadableException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 400错误
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler({MissingServletRequestParameterException.class})
	public BaseResponse requestMissingServletRequest(MissingServletRequestParameterException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 405错误
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public BaseResponse request405(HttpRequestMethodNotSupportedException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 406错误
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
	public BaseResponse request406(HttpMediaTypeNotAcceptableException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 500错误
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
	public BaseResponse server500(RuntimeException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}

	/**
	 * 栈溢出
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler({StackOverflowError.class})
	public BaseResponse requestStackOverflow(StackOverflowError e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);

	}

	/**
	 * 除数不能为0
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler({ArithmeticException.class})
	public BaseResponse arithmeticException(ArithmeticException e) {
		log.error("errorInfo:{},errorStackTrace:{}",e.getMessage(),e.getStackTrace());
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);

	}

	/**
	 * 处理自定义的业务异常
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler(value = BizException.class)
	public BaseResponse bizExceptionHandler(BizException e){
		log.error("发生业务异常！原因是:{}",e.getErrorMsg());
		return BaseResponse.error(e.getErrorCode(),e.getErrorMsg());
	}


	/**
	 * 处理其他异常
	 * @param e e
	 * @return BaseResponse
	 */
	@ExceptionHandler(value =Exception.class)
	public BaseResponse exceptionHandler(Exception e){
		log.error("未知异常！原因是:",e);
		return BaseResponse.error(ErrorCodeEnum.SYSTEM_ERROR);
	}
}
