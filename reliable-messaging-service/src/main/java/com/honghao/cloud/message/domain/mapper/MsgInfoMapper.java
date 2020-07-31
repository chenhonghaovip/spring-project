package com.honghao.cloud.message.domain.mapper;

import com.honghao.cloud.message.domain.entity.MsgInfo;

import java.util.List;

public interface MsgInfoMapper {
    /**
     * 删除数据
     * @param msgId 主键
     * @return int
     */
    int deleteByPrimaryKey(Long msgId);
    /**
     * 全部插入
     * @param record record
     * @return int
     */
    int insert(MsgInfo record);
    /**
     * 选择插入
     * @param record record
     * @return int
     */
    int insertSelective(MsgInfo record);

    /**
     * 主键查询
     * @param msgId 主键
     * @return MsgInfo
     */
    MsgInfo selectByPrimaryKey(Long msgId);

    /**
     * 根据数据更新
     * @param record record
     * @return int
     */
    int updateByPrimaryKeySelective(MsgInfo record);

    /**
     * 更新全部
     * @param record record
     * @return int
     */
    int updateByPrimaryKey(MsgInfo record);

    /**
     * 更新全部
     * @param record record
     * @return int
     */
    int updateBatch(List<MsgInfo> record);

    /**
     * 查询改状态下的所有消息数据
     * @return List<MsgInfo>
     */
    List<MsgInfo> selectByStatus();
}