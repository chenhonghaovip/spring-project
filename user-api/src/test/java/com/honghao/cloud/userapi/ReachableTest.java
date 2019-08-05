package com.honghao.cloud.userapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单文件测试
 *
 * @author chenhonghao
 * @date 2019-07-26 09:43
 */
@Slf4j
public class ReachableTest {
    private Jedis jedis;

    @Before
    public void before() {
        jedis = new Jedis("127.0.0.1");
    }

    /**
     * 简单添加
     */
    @Test
    public void test01(){
        jedis.flushAll();
        String name = "name";
        String value = "qq";
        jedis.set(name, value);
        System.out.println("追加前：" + jedis.get(name)); // 追加前：qq

        // 在原有值得基础上添加,如若之前没有该key，则导入该key
        jedis.append(name, "ww");
        System.out.println("追加后：" + jedis.get(name)); // 追加后：qqww

        jedis.append("id", "ee");
        System.out.println("没此key：" + jedis.get(name));
        System.out.println("get此key：" + jedis.get("id"));
    }
    /**
     * 简单添加
     */
    @Test
    public void test02(){
        jedis.flushAll();
        jedis.mset("name1", "aa", "name2", "bb", "name3", "cc");
        System.out.println(jedis.mget("name1", "name2", "name3"));
        jedis.del("name1");
    }
    /**
     * map添加
     */
    @Test
    public void test03(){
        jedis.flushAll();
        Map<String, String> map = new HashMap<>();
        map.put("name", "fujianchao");
        map.put("password", "123");
        map.put("age", "12");
        // 存入一个map
        jedis.hmset("user", map);

        // map key的个数
        System.out.println("map的key的个数" + jedis.hlen("user"));

        // map key
        System.out.println("map的key" + jedis.hkeys("user"));

        // map value
        System.out.println("map的value" + jedis.hvals("user"));

        // (String key, String... fields)返回值是一个list
        List<String> list = jedis.hmget("user", "age", "name");
        System.out.println("redis中key的各个 fields值："
                + jedis.hmget("user", "age", "name") + list.size());

        // 删除map中的某一个键 的值 password
        // 当然 (key, fields) 也可以是多个fields
        jedis.hdel("user", "age");

        System.out.println("删除后map的key" + jedis.hkeys("user"));
    }
    /**
     * 简单添加
     */
    @Test
    public void test04(){
        jedis.flushAll();
        jedis.lpush("list", "aa");
        jedis.lpush("list", "bb");
        jedis.lpush("list", "cc");
        System.out.println(jedis.lrange("list", 0, -1));
        System.out.println(jedis.lrange("list", 0, 1));
        System.out.println(jedis.lpop("list")); // 栈顶
        jedis.del("list");
    }
    /**
     * 简单添加
     */
    @Test
    public void test05(){

    }
}
