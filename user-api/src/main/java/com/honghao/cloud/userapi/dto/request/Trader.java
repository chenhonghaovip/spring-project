package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhonghao
 * @date 2019-12-03 17:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trader {
    private String name;
    private String city;
}
