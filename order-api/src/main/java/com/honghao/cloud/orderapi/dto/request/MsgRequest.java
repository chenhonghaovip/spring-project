package com.honghao.cloud.orderapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 消息查询返回结果
 *
 * @author chenhonghao
 * @date 2020-09-24 13:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgRequest {
    /**
     * 订单id和消息id集合
     */
    private List<MsgDTO> list;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MsgDTO {
        /**
         * 第三方业务id
         */
        private String businessId;

        /**
         * 对应的消息id
         */
        private Long msgId;
    }
}
