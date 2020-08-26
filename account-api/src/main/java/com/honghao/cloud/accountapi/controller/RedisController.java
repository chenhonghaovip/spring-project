package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.accountapi.dto.request.LikePointVO;
import com.honghao.cloud.accountapi.service.RedisService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * redis测试
 *
 * @author chenhonghao
 * @date 2020-07-24 09:41
 */
@Slf4j
@Api(value = "redis测试使用" ,tags = "redis测试使用")
@RestController
@RequestMapping("/redisController")
public class RedisController {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(100,500,"123");
    @Resource
    private RedisService redisService;

    /**
     * redis删除bigkey
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/addBigData")
    @ApiOperation(value = "redis添加大数据", notes = "redis添加大数据")
    public BaseResponse addBigData(@RequestBody String userId){
        return redisService.addBigData(userId);
    }

    /**
     * redis删除bigkey
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/delBigHash")
    @ApiOperation(value = "redis删除bigkey", notes = "redis删除bigkey")
    public BaseResponse delBigHash(@RequestBody String userId){
        return redisService.delBigHash(userId);
    }


    /**
     * redis缓存击穿处理
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/cacheBreakdown")
    @ApiOperation(value = "redis缓存击穿处理", notes = "redis缓存击穿处理")
    public BaseResponse cacheBreakdown(@RequestBody String userId){
        return redisService.cacheBreakdown(userId);
    }

    /**
     * 缓存穿透
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/cachePenetration")
    @ApiOperation(value = "缓存穿透", notes = "缓存穿透")
    public BaseResponse cachePenetration(@RequestBody String userId){
        return redisService.cachePenetration(userId);
    }

    /**
     * redis加锁操作
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisLock")
    @ApiOperation(value = "redis加锁操作", notes = "redis加锁操作")
    public BaseResponse redisLock(@RequestBody String userId){
        return redisService.redisLock(userId);
    }

    /**
     * redisson加锁操作
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redissonLock")
    @ApiOperation(value = "redisson加锁操作", notes = "redisson加锁操作")
    public BaseResponse redissonLock(@RequestBody String userId){
        return redisService.redissonLock(userId);
    }

    /**
     * redis实现list
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisList")
    @ApiOperation(value = "redis实现list", notes = "redis实现list")
    public BaseResponse redisList(@RequestBody String userId){
        return redisService.redisList(userId);
    }

    /**
     * redis实现set
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisSet")
    @ApiOperation(value = "redis实现set", notes = "redis实现set")
    public BaseResponse redisSet(@RequestBody String userId){
        return redisService.redisSet(userId);
    }

    /**
     * redis实现Zset
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisSortedSet")
    @ApiOperation(value = "redis实现Zset", notes = "redis实现Zset")
    public BaseResponse redisSortedSet(@RequestBody String userId){
        return redisService.redisSortedSet(userId);
    }

    /**
     * redis实现Geo
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisGeo")
    @ApiOperation(value = "redis实现Geo", notes = "redis实现Geo")
    public BaseResponse redisGeo(@RequestBody String userId){
        return redisService.redisGeo(userId);
    }

    /**
     * redis获取服务器信息
     * @param userId userId
     * @return BaseResponse
     */
    @PostMapping("/redisInfo")
    @ApiOperation(value = "redis获取服务器信息", notes = "redis获取服务器信息")
    public BaseResponse redisInfo(@RequestBody String userId){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute((()->{
                try {
                    cyclicBarrier.await();
                    redisService.redisInfo(userId);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }));
        }
        return BaseResponse.success();
    }


    /**
     * redis模拟实现微博热搜排行榜（点击）
     * @return BaseResponse
     */
    @PostMapping("/hotSearchOnWeibo")
    @ApiOperation(value = "redis模拟实现微博热搜排行榜（点击）", notes = "redis模拟实现微博热搜排行榜（点击）")
    public BaseResponse hotSearchOnWeibo(@RequestBody String key){
        return redisService.hotSearchOnWeibo(key);
    }



    /**
     * 微博热搜查询
     * @param type 查询类型
     * @return BaseResponse
     */
    @PostMapping("/getHot")
    @ApiOperation(value = "微博热搜查询", notes = "微博热搜查询")
    public BaseResponse getHot(@RequestBody String type){
        return redisService.getHot(type);
    }

    /**
     * 点赞功能
     * @param likePointVO 查询类型
     * @return BaseResponse
     */
    @PostMapping("/likePoint")
    @ApiOperation(value = "点赞功能", notes = "点赞功能")
    public BaseResponse likePoint(@RequestBody LikePointVO likePointVO){
        return redisService.likePoint(likePointVO);
    }

    /**
     * 朋友圈点赞总数
     * @param likePointVO likePointVO
     * @return BaseResponse
     */
    @PostMapping("/likePointCount")
    @ApiOperation(value = "朋友圈点赞总数", notes = "朋友圈点赞总数")
    public BaseResponse likePointCount(@RequestBody LikePointVO likePointVO){
        return redisService.likePointCount(likePointVO);
    }


    /**
     * 朋友圈点赞和取消点赞功能实现
     * @param likePointVO likePointVO
     * @return BaseResponse
     */
    @PostMapping("/isLikePoint")
    @ApiOperation(value = "朋友圈点赞和取消点赞功能实现", notes = "朋友圈点赞和取消点赞功能实现")
    public BaseResponse isLikePoint(@RequestBody LikePointVO likePointVO){
        return redisService.isLikePoint(likePointVO);
    }

    /**
     * redis发布与订阅功能
     * @param userId userId
     * @return BaseResponse
     */
    @GetMapping("/pubAndSub")
    @ApiOperation(value = "redis发布与订阅功能", notes = "redis发布与订阅功能")
    public BaseResponse pubAndSub(@RequestParam("userId") String userId){
        return redisService.pubAndSub(userId);
    }
}
