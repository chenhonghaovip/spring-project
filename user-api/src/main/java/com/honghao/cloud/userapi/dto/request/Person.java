package com.honghao.cloud.userapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author chenhonghao
 * @date 2019-12-04 15:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    private Optional<Car> car;
}
