package com.honghao.cloud.message.domain.mapper;

import com.honghao.cloud.message.domain.entity.MsgInfo;
import org.apache.ibatis.annotations.Param;

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
     * @param msgStatus 消息状态
     * @return int
     */
    int updateBatch(@Param("list") List<Long> record,@Param("msgStatus") int msgStatus);

    /**
     * 查询改状态下的所有消息数据
     * @return List<MsgInfo>
     */
    List<MsgInfo> selectByStatus();
}