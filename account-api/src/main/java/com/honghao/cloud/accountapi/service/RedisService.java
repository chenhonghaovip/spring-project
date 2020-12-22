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
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse addBigData(String userId);

    /**
     * redis删除bigkey
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse delBigHash(String userId);

    /**
     * redis缓存击穿处理
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse cacheBreakdown(String userId);

    /**
     * 缓存穿透
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse cachePenetration(String userId);

    /**
     * redis加锁操作
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisLock(String userId);

    /**
     * redis实现list
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisList(String userId);

    /**
     * redis实现Set
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisSet(String userId);

    /**
     * redis实现ZSet
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisSortedSet(String userId);


    /**
     * redis实现HyperLogLog
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisHyperLogLog(String userId);


    /**
     * redis实现ZSet
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisGeo(String userId);

    /**
     * redis获取服务器信息
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisInfo(String userId);

    /**
     * redis模拟实现微博热搜排行榜（点击）
     *
     * @param key 请求参数
     * @return BaseResponse
     */
    BaseResponse hotSearchOnWeibo(String key);

    /**
     * 查询微博热搜数据
     *
     * @param type 查询类型
     * @return 热搜数据
     */
    BaseResponse getHot(String type);

    /**
     * 朋友圈点赞和取消点赞功能实现
     *
     * @param likePointVO likePointVO
     * @return BaseResponse
     */
    BaseResponse likePoint(LikePointVO likePointVO);


    /**
     * 朋友圈点赞总数
     *
     * @param likePointVO likePointVO
     * @return BaseResponse
     */
    BaseResponse likePointCount(LikePointVO likePointVO);


    /**
     * 朋友圈点赞和取消点赞功能实现
     *
     * @param likePointVO likePointVO
     * @return BaseResponse
     */
    BaseResponse isLikePoint(LikePointVO likePointVO);

    /**
     * redis发布与订阅功能
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse pubAndSub(String userId);

    /**
     * redis并发抢购
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redisConcurrent(String userId);

    /**
     * 获取redis监控信息
     *
     * @param param param
     * @return Properties
     */
    Properties info(String param);

    /**
     * redis滑动窗口计数器限流
     *
     * @param param param
     * @return Properties
     */
    BaseResponse slidingWindowCounter(String param);

    /**
     * redis滑动窗口计数器限流升级版本-改进使用lua脚本，防止redis高并发下重复操作问题
     *
     * @param param param
     * @return Properties
     */
    BaseResponse slidingWindowCounterUpdate(String param);

    /**
     * redis令牌桶限流
     * 系统会按恒定1/QPS时间间隔(如果QPS=100,则间隔是10ms)往桶里加入Token，
     * 如果桶已经满了就不再加了.新请求来临时,会各自拿走一个Token,如果没有Token就拒绝服务。
     *
     * @param param param
     * @return Properties
     */
    BaseResponse tokenBucket(String param);

    /**
     * redis漏桶限流
     *
     * @param param param
     * @return Properties
     */
    BaseResponse leakyBucket(String param);
}
