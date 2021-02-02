package com.honghao.cloud.accountapi.service;

import com.honghao.cloud.basic.common.base.BaseResponse;

/**
 * 服务限流
 *
 * @author chenhonghao
 * @date 2021-01-21 15:26
 */
public interface LimitService {

    /**
     * 单机简单滑动窗口
     * @return BaseResponse
     */
    BaseResponse singleMachineSlidingWindow();

    /**
     * 单机令牌桶
     * @return BaseResponse
     */
    BaseResponse singleTokenBucket();

    /**
     * 单机漏桶
     * @return BaseResponse
     */
    BaseResponse singleLeakyBucket();

    /**
     * redis滑动窗口计数器限流升级版本-改进使用lua脚本，防止redis高并发下重复操作问题
     *
     * @param param param
     * @return Properties
     */
    BaseResponse redisSlidingWindow(String param);

    /**
     * redis令牌桶限流
     * 系统会按恒定1/QPS时间间隔(如果QPS=100,则间隔是10ms)往桶里加入Token，
     * 如果桶已经满了就不再加了.新请求来临时,会各自拿走一个Token,如果没有Token就拒绝服务。
     *
     * @param param param
     * @return Properties
     */
    BaseResponse redisTokenBucket(String param);

}
