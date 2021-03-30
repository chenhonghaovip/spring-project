package com.honghao.cloud.leetcode.load_balancing;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 随机选择算法--在基数足够大的时候，访问次数就会越相近
 *
 * @author chenhonghao
 * @date 2021-03-30 13:27
 */
public class LoadBalancing {
    private static List<String> ips;
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(Integer.MAX_VALUE - 50);

    static {
        ips = new ArrayList<>();
        ips.add("192.168.1.2");
        ips.add("192.168.1.3");
        ips.add("192.168.1.4");
        ips.add("192.168.1.5");
    }

    /**
     * ip信息和加权比例
     */
    private static HashMap<String, Integer> serverMap = new HashMap<>();

    static {
        serverMap.put("192.168.1.2", 1);
        serverMap.put("192.168.1.3", 1);
        serverMap.put("192.168.1.4", 1);
        serverMap.put("192.168.1.5", 2);
    }

    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            test02();
        }
    }

    @Test
    public void test01() {
        int randomInt = new Random().nextInt(ips.size());
        System.out.println(ips.get(randomInt));
    }

    @Test
    public void test02() {
        int andIncrement = ATOMIC_INTEGER.getAndIncrement();
        System.out.println(andIncrement % ips.size());
    }

    @Test
    public void test03() {
        // 将权重比数据放入到list中，权重为2的就放2个数据，权重为n，则放入n个数据到list集合中
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : serverMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                list.add(entry.getKey());
            }
        }

        int andIncrement = ATOMIC_INTEGER.getAndIncrement();
        System.out.println(andIncrement % list.size());
    }

    @Test
    public void test04() {
        // 将权重比数据放入到list中，权重为2的就放2个数据，权重为n，则放入n个数据到list集合中
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : serverMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                list.add(entry.getKey());
            }
        }

        int value = new Random().nextInt(list.size());
        System.out.println(list.get(value));
    }

    /**
     * IP-Hash
     */
    @Test
    public void test05() {
        String ip = "";
        int value = ip.hashCode() % ips.size();
        System.out.println(ips.get(value));
    }

}
