package com.honghao.cloud.userapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author chenhonghao
 * @date 2020-02-29 15:32
 * <p>
 * java8 内置的四大核心函数式接口
 * <p>
 * Consumer<T>: 消费型接口，接收数据并处理
 * void accept(T t);
 * Supplier<T>: 供给型接口，对外提供数据
 * T get()
 * Function<T, R>: 函数型接口，接收参数，返回结果
 * R apply(T t);
 * Predicate<T>: 断言型接口，检测入参是否符合条件（符合则返回true）
 * boolean test(T t);
 */
@Slf4j
public class TestLambda {

    /**
     * Consumer<T> 消费型接口
     */
    @Test
    public void testConsumer() {
        happy(BigDecimal.valueOf(1000), m -> System.out.println("大保健，消费：" + m + " 元"));
    }

    private void happy(BigDecimal money, Consumer<BigDecimal> consumer) {
        consumer.accept(money);
    }

    /**
     * Supplier是供给型接口
     */
    @Test
    public void testSupplier() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        System.out.println(numList);
    }

    private List<Integer> getNumList(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int n = supplier.get();
            list.add(n);
        }
        return list;
    }

    /**
     * Function<T,R> 函数型接口
     */
    @Test
    public void testFunction() {
        String str = strHandler("我最帅", (x) -> (x + ", 哈哈哈"));
        System.out.println(str);
    }

    private String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    /**
     * Predicate<T> 断言型接口
     */
    @Test
    public void testPredicate() {
        List<String> list = Arrays.asList("Hello", "World", "www.exact.com");
        //函数式接口Predicate主要用于判断，x -> (x.length() > 5) 这里是判断入参的长度是否大于5
        List<String> filterStr = filterStr(list, x -> (x.length() > 5));
        System.out.println(filterStr);
    }

    private List<String> filterStr(List<String> list, Predicate<String> pre) {
        List<String> strList = new ArrayList<>();
        strList.forEach(each -> System.out.println(""));
        for (String str : list) {
            if (pre.test(str)) {
                strList.add(str);
            }
        }
        return strList;
    }
}
