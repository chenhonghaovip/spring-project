package com.honghao.cloud.userapi.test;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.dto.test.Loo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenhonghao
 * @date 2020-01-07 10:46
 */
@Slf4j
public class Test01 {
    private static ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(()->new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1000));
    private static AtomicInteger longAdder = new AtomicInteger(1);
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
    @Test
    public void test(){
        List<Future<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            int num = longAdder.getAndIncrement();
            Future<Integer> future = poolExecutor.submit(()->getNum(num));
            list.add(future);
        }

        list.forEach(each->{
            try {
                int num = each.get(50,TimeUnit.MILLISECONDS);
                System.out.println("执行完成"+num);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                System.out.println("超时取消");
                each.cancel(true);
            }
        });

    }

    private int getNum(int num) throws InterruptedException {
        Thread.sleep(300);
        System.out.println("执行："+num);
        return num;
    }

    @Test
    public void test11(){
        Loo loo = new Loo();
        if (loo.getLocalDateTime()==null){
            System.out.println(JSON.toJSONString(loo));
        }

    }

}
