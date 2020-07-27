package com.honghao.cloud.accountapi.dto.request;

import lombok.Data;

/**
 * 点赞数据
 *
 * @author chenhonghao
 * @date 2020-07-25 15:10
 */
@Data
public class LikePointVO {
    /**
     * 朋友圈id
     */
    private String id;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 状态（点赞或者取消点赞）
     */
    private Boolean status;
}
