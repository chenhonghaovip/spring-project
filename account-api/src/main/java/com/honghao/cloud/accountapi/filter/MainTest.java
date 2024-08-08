package com.honghao.cloud.accountapi.filter;

import com.honghao.cloud.basic.common.base.BaseRequest;
import com.honghao.cloud.basic.common.base.BaseResponse;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;

import java.io.StringWriter;

/**
 * 责任链模式
 *
 * @author chenhonghao
 * @date 2020-09-14 15:01
 */
public class MainTest {

    @Test
    public void test() {
        // 初始化Velocity引擎
        VelocityEngine ve = new VelocityEngine();
        ve.init();

        // 创建上下文对象
        VelocityContext ctx = new VelocityContext();

        // 模拟数据
        ctx.put("name", "World");

        // 使用Velocity解析模板字符串
        String template = "Hello $name!";
        StringWriter stringWriter = new StringWriter();
        ve.evaluate(ctx, stringWriter, "", template);

        // 输出结果
        System.out.println(stringWriter);

        //过滤请求
        BaseRequest request = new BaseRequest();
        //set方法，将待处理字符串传递进去
        //处理过程结束，给出的响应
        BaseResponse response = new BaseResponse();


        //FilterChain,过滤规则形成的拦截链条
        FilterChain fc = new FilterChain();
        //规则链条添加过滤规则，采用的是链式调用
        fc.addFilter(new HTMLFilter())
                .addFilter(new FaceFilter());
        //按照FilterChain的规则顺序，依次应用过滤规则
        fc.doFilter(request, response, fc);
    }

    @Test
    public void test1() {
        String key = "20200113";
        System.out.println(Math.abs(key.hashCode()) % 100);
    }
}
