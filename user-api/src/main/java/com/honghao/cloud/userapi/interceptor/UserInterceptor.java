package com.honghao.cloud.userapi.interceptor;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.aspect.Auth;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.dto.common.TokenInfoDTO;
import com.honghao.cloud.userapi.dto.request.Operator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 用户信息拦截器
 *
 * @author chenhonghao
 * @date 2019-07-18 19:58
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {
    /**
     * 超级管理员编号
     */
    private final String REQUEST_HEADER_USER_TAG = "tag";



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("类型为：{}",handler.getClass());
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            Auth annotation = method.getAnnotation(Auth.class);
            if (ObjectUtils.isEmpty(annotation)){
                annotation = handlerMethod.getBeanType().getAnnotation(Auth.class);
            }
            if (!ObjectUtils.isEmpty(annotation)){
                TokenInfoDTO tokenInfoDTO = TokenInfoDTO.builder().build();
                if (!validateTokenInfo(request,response)){
                    return false;
                }
            }
            Operator operator = Operator.builder().agentNo("111111").build();
            UserInfoHolder.setOperator(operator);
            return true;
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //请求完成后删除用户信息
        UserInfoHolder.removeOperator();
    }

    /**
     * 请求头信息校验和信息封装
     *
     * @param request 请求报文
     * @param response 返回报文
     * @return true/false
     */
    private Boolean validateTokenInfo(HttpServletRequest request,HttpServletResponse response){
        String tag;
        if (StringUtils.isBlank((tag = request.getHeader(REQUEST_HEADER_USER_TAG)))){
            log.info("header中用户tag信息缺失:{}", tag);
            response(response, missInfoResponse("请求中tag信息缺失"));
            return false;
        }
        log.info("请求报文信息：{}",request.getParameterMap());
        return true;
    }

    /**
     * 封装返回信息
     * @param response 返回报文
     * @param message 返回的信息
     */
    private void response(HttpServletResponse response, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(message);
        } catch (IOException e) {
            log.error("interceptor response error", e);
        }
    }

    /**
     * 用户信息缺少提示语
     *
     * @return
     * @param message
     */
    private String missInfoResponse(String message) {
        return JSON.toJSONString(BaseResponse.error(message));
    }
}
