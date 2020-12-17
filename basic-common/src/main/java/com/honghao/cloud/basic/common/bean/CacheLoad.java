package com.honghao.cloud.basic.common.bean;

/**
 * @author chenhonghao
 * @date 2020-07-25 16:15
 */
@FunctionalInterface
public interface CacheLoad<T> {
    /**
     * 运行方法，返回T类型结果
     *
     * @return T
     */
    T run();
}
