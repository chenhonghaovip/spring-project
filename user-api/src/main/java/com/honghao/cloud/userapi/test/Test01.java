package com.honghao.cloud.userapi.test;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenhonghao
 * @date 2020-01-07 10:46
 */
@Slf4j
public class Test01 {
    private static ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(()->new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1000));

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        Map map = new HashMap(8);
        map.put("1","1");
        map.put("9","9");
        // TODO: 2020/1/7 测试simpleDateFormat的线程安全性
//        simpleDateFormat.format(new Date());

//        while (true) {
//            poolExecutor.execute(() -> {
//                String dateString = threadLocal.get().format(new Date());
//                try {
//                    Date parseDate = threadLocal.get().parse(dateString);
//                    String dateString2 = threadLocal.get().format(parseDate);
//                    System.out.println(dateString.equals(dateString2));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
    }
}
