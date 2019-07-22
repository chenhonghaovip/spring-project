package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO
 *
 * @author chenhonghao
 * @date 2019-07-22 21:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO implements Serializable {

    private static final long serialVersionUID = -5451377588975475828L;
    private Integer code;
    private String desc;
}
