package com.honghao.cloud.userapi.domain.mapper.slave;

import com.honghao.cloud.userapi.domain.entity.CloudDeliveryMan;

public interface CloudOrderMapper {
    int deleteByPrimaryKey(String deliveryManId);

    int insert(CloudDeliveryMan record);

    int insertSelective(CloudDeliveryMan record);

    CloudDeliveryMan selectByPrimaryKey(String deliveryManId);

    int updateByPrimaryKeySelective(CloudDeliveryMan record);

    int updateByPrimaryKey(CloudDeliveryMan record);
}