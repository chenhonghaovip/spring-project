package com.honghao.cloud.accountapi.filter;

import com.honghao.cloud.basic.common.base.BaseRequest;
import com.honghao.cloud.basic.common.base.BaseResponse;

/**
 * 抽象过滤器
 *
 * @author chenhonghao
 * @date 2020-09-14 14:49
 */
public interface Filter {
    /**
     * 过滤器执行
     * @param request request
     * @param response response
     * @param chain chain
     */
    void doFilter(BaseRequest request, BaseResponse response, FilterChain chain);
}
