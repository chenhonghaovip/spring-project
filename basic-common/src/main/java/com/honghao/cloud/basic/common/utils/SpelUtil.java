package com.honghao.cloud.basic.common.utils;

import com.google.common.collect.Lists;
import com.honghao.cloud.basic.common.dto.RpcMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author chenhonghao12
 * @version 1.0
 */
public class SpelUtil {
    private static final ExpressionParser parser = new SpelExpressionParser();
    private static final DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public static String parseSpEl(Method method, Object[] args, String spEl) {
        //解析参数名
        String[] params = parameterNameDiscoverer.getParameterNames(method);
        if (params == null) {
            return null;
        }
        //el解析需要的上下文对象
        EvaluationContext context = new StandardEvaluationContext();
        //所有参数都作为原材料扔进去
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }
        Expression expression = parser.parseExpression(spEl);
        return expression.getValue(context, String.class);
    }


    public static <T> T parseSpEl(Method method, Object[] args, String spEl, Class<T> tClass) {
        //解析参数名
        String[] params = parameterNameDiscoverer.getParameterNames(method);
        if (params == null) {
            return null;
        }
        //el解析需要的上下文对象
        EvaluationContext context = new StandardEvaluationContext();
        //所有参数都作为原材料扔进去
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }
        Expression expression = parser.parseExpression(spEl);
        return expression.getValue(context, tClass);
    }

    public static String getMethodKey(Method method) {
        return method.getDeclaringClass() + "#" + method.getName();
    }


    public static void main(String[] args) {

        List<Class<?>> classes = Lists.newArrayList();
        classes.add(String.class);
        classes.add(List.class);

        Method[] declaredMethods = SpelUtil.class.getDeclaredMethods();
        Method getResult = Arrays.stream(declaredMethods).filter(each -> Objects.equals(each.getName(), "getResult")).findFirst().orElse(null);
        Object[] r = new Object[3];
        r[0] = "1";
        r[1] = Lists.newArrayList("1", "2", "4");
        r[2] = RpcMessage.builder().id(8).messageType(1).body(new Object()).build();

        if (Objects.nonNull(getResult)) {
            String spel = "#t1[0] && #rpcMessage.id";

            StringBuilder stringBuilder = new StringBuilder();
            Arrays.stream(spel.split("&&"))
                    .map(String::trim).filter(StringUtils::isNotBlank).forEach(each -> {
                        String str = SpelUtil.parseSpEl(getResult, r, each);
                        stringBuilder.append(":").append(str);
                        System.out.println(str);
                    });

            System.out.println(stringBuilder);


        }

    }


    public Object getResult(String t, List<String> t1, RpcMessage rpcMessage) {
        System.out.println(111);
        return null;
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        VARCHAR("VARCHAR", String.class) {
            @Override
            public <T> T getInfo() {
                return null;
            }
        };
        private String code;

        private Class<?> aClass;

        public abstract <T> T getInfo();
    }

}
