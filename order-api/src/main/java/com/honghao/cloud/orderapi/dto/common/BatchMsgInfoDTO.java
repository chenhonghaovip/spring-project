package com.honghao.cloud.orderapi.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author CHH
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchMsgInfoDTO {
    private List<MsgInfoDTO> msgList;

    private List<Long> msgIds;
}