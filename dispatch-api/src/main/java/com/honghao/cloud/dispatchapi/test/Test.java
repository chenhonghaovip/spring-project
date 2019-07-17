package com.honghao.cloud.dispatchapi.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenhonghao
 * @date 2019-07-17 09:23
 */
public class Test {
    public static void main(String[] args) {
            String[] str1={"a","b","c","d","e"};
            String[] str2={"d","e","f","g","h"};

            Set<String> set1 = new HashSet<>(Arrays.asList(str1));
            Set<String> set2 = new HashSet<>(Arrays.asList(str2));
            Set<String> set = new HashSet<>();

            // 并集
            set.addAll(set1);
            set.addAll(set2);
            System.out.println("并集" + set);

            // 交集
            set.clear();
            set.addAll(set1);
            set.retainAll(set2);
            System.out.println("交集" + set);
            // 差集
            set.clear();
            set.addAll(set1);
            set.removeAll(set2);
            System.out.println("差集" + set);

    }
}
