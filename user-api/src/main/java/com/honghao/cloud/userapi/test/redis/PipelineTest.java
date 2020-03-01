package com.honghao.cloud.userapi.test.redis;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * 管道测试工作
 *
 * @author chenhonghao
 * @date 2020-02-20 10:30
 */
@Slf4j
public class PipelineTest {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
//        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.SHORT_IDS.get(""));

        System.out.println(localDate.getDayOfMonth());
//            Jedis jedis = new Jedis("49.235.212.2",6379);
//            long startTime = System.currentTimeMillis();
//            for (int i = 0; i < 10000; i++) {
//                jedis.set(String.valueOf(i),String.valueOf(i));
//            }
//            System.out.println("普通jedis操作:"+(System.currentTimeMillis()-startTime));
//
//            Pipeline pipeline = jedis.pipelined();
//
//            long secondTime = System.currentTimeMillis();
//            List<String> keys = new ArrayList<>();
//            for (int i = 10000; i < 20000; i++) {
//                keys.add(String.valueOf(i));
//                pipeline.set(String.valueOf(i),String.valueOf(i));
//            }
//            for (int i = 0; i < 20000; i++) {
//                pipeline.del(String.valueOf(i));
//            }
//            pipeline.sync();
//            System.out.println("pipeline操作:"+(System.currentTimeMillis()-secondTime));

    }
}
