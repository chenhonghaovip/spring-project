package com.honghao.cloud.basic.common.base.bean;

/**
 * @author chenhonghao
 * @date 2020-07-25 16:15
 */
@FunctionalInterface
public interface CacheLoad<T> {
   T run();
}
