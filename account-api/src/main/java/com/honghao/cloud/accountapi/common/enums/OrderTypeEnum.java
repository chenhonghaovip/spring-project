package com.honghao.cloud.accountapi.common.enums;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.accountapi.dto.common.CommonDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * 订单类型枚举
 *
 * @author chenhonghao
 * @date 2019-09-18 14:03
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = "order.config")
public class OrderTypeEnum {
    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单类型集合
     */
    private List<CommonDTO> orderTypeEnumList;

    @PostConstruct
    private void getOrderTypeEnumList(){
        this.orderTypeEnumList = JSON.parseArray(orderType,CommonDTO.class);
    }

    /**
     * 获取汉字描述
     * @param code code
     * @return desc
     */
    public String getOrderTypeEnum(Integer code){
        if (!CollectionUtils.isEmpty(orderTypeEnumList)){
            Optional<CommonDTO> commonDTO = orderTypeEnumList.stream().filter(each -> each.getCode().equals(code)).findFirst();
            if (commonDTO.isPresent()){
                return commonDTO.get().getDesc();
            }
            return StringUtils.EMPTY;
        }
        return StringUtils.EMPTY;
    }
}
