package com.honghao.cloud.leetcode.simple;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * @author chenhonghao12
 * @version 1.0
 */
public class SimpleTest {

    @Test
    public void test() {
        int[] ints = test1();
        System.out.println(ints);
    }

    public int[] test1() {
        int[] a = new int[]{2, 7, 11, 15};
        int target = 9;

        Map<Integer, Integer> maps = Maps.newConcurrentMap();
        for (int i = 0; i < a.length; i++) {
            int key = a[i];
            int key1 = target - key;
            if (maps.containsKey(key1)) {
                return new int[]{i, maps.get(key1)};
            } else {
                maps.put(key, i);
            }
        }
        return null;
    }
}
