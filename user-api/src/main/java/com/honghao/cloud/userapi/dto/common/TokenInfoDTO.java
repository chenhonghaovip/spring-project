package com.honghao.cloud.userapi.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * header中用户信息
 *
 * @author luxinghui
 * @date 2019-05-18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfoDTO implements Serializable {
    private static final long serialVersionUID = -2370068712432766421L;
    /**
     * 用户工号
     */
    private String code;
    /**
     * 用户名字(已Base64解码)
     */
    private String userName;
    /**
     * 用户属性标签
     * 1:直营，2：渠道人员，3：超管
     */
    private Integer tag;
    /**
     * 属性编码，当请求人非超管是存在值，对应渠道商编码（不可信）
     */
    private String tagCode;
    /**
     * 渠道商编码
     */
    private String agentNo;
}
