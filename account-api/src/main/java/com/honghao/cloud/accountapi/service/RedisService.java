package com.honghao.cloud.accountapi.service;

import com.honghao.cloud.accountapi.dto.request.LikePointVO;
import com.honghao.cloud.basic.common.base.BaseResponse;

import java.util.Properties;

/**
 * @author chenhonghao
 * @date 2020-07-23 21:31
 */
public interface RedisService {
    /**
     * redis添加大量数据
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse addBigData(String userId);
    /**
     * redis删除bigkey
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse delBigHash(String userId);
    /**
     * redis缓存击穿处理
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse cacheBreakdown(String userId);

    /**
     * 缓存穿透
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse cachePenetration(String userId);

    /**
     * redis加锁操作
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisLock(String userId);

    /**
     * redisson加锁操作
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redissonLock(String userId);

    /**
     * redis实现list
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisList(String userId);

    /**
     * redis实现Set
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisSet(String userId);

    /**
     * redis实现ZSet
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisSortedSet(String userId);


    /**
     * redis实现ZSet
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisGeo(String userId);

    /**
     * redis获取服务器信息
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisInfo(String userId);

    /**
     * redis模拟实现微博热搜排行榜（点击）
     * @param key 请求参数
     * @return BaseResponse
     */
    BaseResponse hotSearchOnWeibo(String key);

    /**
     * 查询微博热搜数据
     * @param type 查询类型
     * @return 热搜数据
     */
    BaseResponse getHot(String type);

    /**
     * 朋友圈点赞和取消点赞功能实现
     * @param likePointVO likePointVO
     * @return BaseResponse
     */
    BaseResponse likePoint(LikePointVO likePointVO);


    /**
     * 朋友圈点赞总数
     * @param likePointVO likePointVO
     * @return BaseResponse
     */
    BaseResponse likePointCount(LikePointVO likePointVO);


    /**
     * 朋友圈点赞和取消点赞功能实现
     * @param likePointVO likePointVO
     * @return BaseResponse
     */
    BaseResponse isLikePoint(LikePointVO likePointVO);

    /**
     * redis发布与订阅功能
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse pubAndSub(String userId);

    /**
     * redis并发抢购
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisConcurrent(String userId);

    /**
     * 获取redis监控信息
     * @param param param
     * @return Properties
     */
    Properties info(String param);
}
