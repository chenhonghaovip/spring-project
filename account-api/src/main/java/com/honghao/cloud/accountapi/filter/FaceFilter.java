package com.honghao.cloud.accountapi.filter;

import com.honghao.cloud.basic.common.base.base.BaseRequest;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import org.springframework.stereotype.Component;

/**
 * 定义FaceFilter
 *
 * @author chenhonghao
 * @date 2020-09-14 15:00
 */
@Component
public class FaceFilter implements Filter{
    @Override
    public void doFilter(BaseRequest request, BaseResponse response, FilterChain chain) {
        //将字符串中出现的":):"转换成"^V^";
        System.out.println("FaceFilter");
        chain.doFilter(request, response, chain);
    }
}
