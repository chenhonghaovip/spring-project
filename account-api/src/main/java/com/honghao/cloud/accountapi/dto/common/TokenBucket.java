package com.honghao.cloud.accountapi.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 令牌桶
 *
 * @author chenhonghao
 * @date 2021-01-21 17:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenBucket {
    /**
     * 最大容量
     */
    private int capacity;

    /**
     * 当前桶中令牌数量
     */
    private int currentSize;
    /**
     * 速度(/s)
     */
    private int rate;

    /**
     * 令牌的最后生成时间
     */
    private long lastRefillTime;
}
