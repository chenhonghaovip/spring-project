package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.accountapi.service.RedisService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * redis测试
 *
 * @author chenhonghao
 * @date 2020-07-24 09:41
 */
@RestController
@RequestMapping("/redisController")
public class RedisController {
    @Resource
    private RedisService redisService;

    /**
     * redis缓存击穿处理
     * @param userId userId
     * @return BaseResponse
     */
    @ApiOperation(value = "userId", notes = "redis缓存击穿处理")
    @PostMapping("/cacheBreakdown")
    public BaseResponse cacheBreakdown(@RequestBody String userId){
        return redisService.cacheBreakdown(userId);
    }

    /**
     * 缓存穿透
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/cachePenetration")
    public BaseResponse cachePenetration(@RequestBody String userId){
        return redisService.cachePenetration(userId);
    }

    /**
     * redis加锁操作
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisLock")
    public BaseResponse redisLock(@RequestBody String userId){
        return redisService.redisLock(userId);
    }

    /**
     * redisson加锁操作
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redissonLock")
    public BaseResponse redissonLock(@RequestBody String userId){
        return redisService.redissonLock(userId);
    }

    /**
     * redis实现list
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisList")
    public BaseResponse redisList(@RequestBody String userId){
        return redisService.redisList(userId);
    }

    /**
     * redis实现set
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisSet")
    public BaseResponse redisSet(@RequestBody String userId){
        return redisService.redisSet(userId);
    }

    /**
     * redis实现Zset
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisZSet")
    public BaseResponse redisZSet(@RequestBody String userId){
        return redisService.redisZSet(userId);
    }

    /**
     * redis模拟实现微博热搜排行榜（点击）
     * @return BaseResponse
     */
    @PostMapping("/hotSearchOnWeibo")
    public BaseResponse hotSearchOnWeibo(@RequestBody String key){
        return redisService.hotSearchOnWeibo(key);
    }


}
