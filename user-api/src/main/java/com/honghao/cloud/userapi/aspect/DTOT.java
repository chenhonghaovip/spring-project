package com.honghao.cloud.userapi.aspect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhonghao
 * @date 2020-05-27 15:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DTOT {
    private String url;

    private String data;

    private Integer seconds;

}
