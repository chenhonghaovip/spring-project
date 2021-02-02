package com.honghao.cloud.accountapi.common.dict;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 公共Map
 *
 * @author chenhonghao
 * @date 2021-02-02 11:17
 */
public class CommonMap {
    /**
     * 商品抢购Map
     */
    public static final ConcurrentHashMap<String,ConcurrentHashMap<String,Boolean>> HASH_MAP = new ConcurrentHashMap<>();
}
