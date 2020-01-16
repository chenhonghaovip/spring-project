package com.honghao.cloud.userapi.test.springtest;

/**
 * @author chenhonghao
 * @date 2020-01-16 09:00
 */
public interface Map<K,V> {
    void put(K k,V v);

    /**
     * get获取值
     * @param k k
     * @return value
     */
    V get(K k);

    interface Entry<K,V>{
        K getKey();

        V getValue();
    }
}
