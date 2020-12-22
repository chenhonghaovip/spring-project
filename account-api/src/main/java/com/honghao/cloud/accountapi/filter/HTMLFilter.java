package com.honghao.cloud.accountapi.filter;

import com.honghao.cloud.basic.common.base.BaseRequest;
import com.honghao.cloud.basic.common.base.BaseResponse;
import org.springframework.stereotype.Component;


/**
 * 处理字符串中的HTML标记
 *
 * @author chenhonghao
 * @date 2020-09-14 14:51
 */
@Component
public class HTMLFilter implements Filter {
    @Override
    public void doFilter(BaseRequest request, BaseResponse response, FilterChain chain) {
        //将字符串中出现的"<>"符号替换成"[]"
        System.out.println("HTMLFilter");
        chain.doFilter(request, response, chain);
    }
}
