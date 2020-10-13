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
     * 删除数据
     * @param msgIds 主键
     * @return int
     */
    int deleteBatch(List<Long> msgIds);
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
     * 批次选择插入
     * @param list list
     * @return int
     */
    int batchInsertSelective(@Param("list") List<MsgInfo> list);

    /**
     * 主键查询
     * @param msgId 主键
     * @return MsgInfo
     */
    MsgInfo selectByPrimaryKey(Long msgId);

    /**
     * 主键查询
     * @param list 主键
     * @return MsgInfo
     */
    List<MsgInfo> selectBatch(@Param("list") List<Long> list);

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
     * 更新全部
     * @param record record
     * @return int
     */
    int updateRetryTimes(@Param("list") List<Long> record);

    /**
     * 查询改状态下的所有消息数据
     * @return List<MsgInfo>
     */
    List<MsgInfo> selectByStatus();
}