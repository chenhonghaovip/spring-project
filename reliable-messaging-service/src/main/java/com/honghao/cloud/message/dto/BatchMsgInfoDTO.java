package com.honghao.cloud.message.dto;

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