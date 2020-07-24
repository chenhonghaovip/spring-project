package com.honghao.cloud.accountapi.service.impl;

import com.honghao.cloud.accountapi.common.enums.ErrorCodeEnum;
import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.accountapi.domain.mapper.ShopInfoMapper;
import com.honghao.cloud.accountapi.dto.common.Dict;
import com.honghao.cloud.accountapi.service.RedisService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reids各个数据测试
 *
 * @author chenhonghao
 * @date 2020-07-23 21:31
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    private Random random = new Random();
    private static List<ShopInfo> ids = new ArrayList<>();
    private ConcurrentHashMap<String, ReentrantLock> concurrentHashMap = new ConcurrentHashMap<>();
    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1);
//    @Resource
//    private Redisson redisson;
    @Resource
    private ShopInfoMapper shopInfoMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void timerTask(){
        SCHEDULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(()->{
            String key = "list12345";
            Object object;
            while (Objects.nonNull(object = redisTemplate.opsForList().rightPop(key))){
                System.out.println(object);
            }
        },0,2,TimeUnit.MINUTES);
    }
    static {
        for (int i = 0; i < 10; i++) {
            String value = String.valueOf(i);
            ids.add(ShopInfo.builder().shopName(value).shopUrl(value).shopId("20200724000000"+i).shopPrice(new BigDecimal(i)).build());
        }
    }

    @Override
    public BaseResponse cacheBreakdown(String userId) {
        Object o = redisTemplate.opsForValue().get(userId);
        if (Objects.nonNull(o)){
            return BaseResponse.success();
        }
        // 缓存未命中时，以用户id为颗粒度加锁，加本地锁，减少访问数据库的次数（如要完全限制只查询一次数据库，使用分布式锁）
        ReentrantLock reentrantLock = new ReentrantLock();
        if ((concurrentHashMap.putIfAbsent(userId,reentrantLock)) != null){
            reentrantLock = concurrentHashMap.get(userId);
        }

        reentrantLock.lock();
        try {
            // 利用DCL(Double Check Lock)双锁检查机制
            Object temp = redisTemplate.opsForValue().get(userId);
            if (Objects.nonNull(temp)){
                return BaseResponse.success();
            }
            // 查询数据库并且放入到缓存中
            ShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(userId);
            redisTemplate.opsForValue().set(userId, shopInfo);
            return BaseResponse.success();
        } finally {
            // 当前锁的等待队列为空时，删除该锁
            if (!reentrantLock.hasQueuedThreads()){
                concurrentHashMap.remove(userId);
            }
            reentrantLock.unlock();
        }
    }

    @Override
    public BaseResponse cachePenetration(String userId) {
        return BaseResponse.success();
    }

    @Override
    public BaseResponse redisLock(String userId) {
        String clientId = UUID.randomUUID().toString();
        try {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(userId, clientId, 10, TimeUnit.SECONDS);
            if (!aBoolean){
                return BaseResponse.error(ErrorCodeEnum.API_GATEWAY_ERROR);
            }

            // 业务处理
        } finally {
            if (clientId.equals(redisTemplate.opsForValue().get(userId))){
                redisTemplate.delete(userId);
            }
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse redissonLock(String userId) {
//        RLock lock = redisson.getLock(userId);
//        lock.lock(10, TimeUnit.SECONDS);

        try {
            return BaseResponse.success();
        } finally {
//            lock.unlock();
        }
    }

    @Override
    public BaseResponse redisList(String userId) {
        String key = "list"+userId;
        redisTemplate.delete(key);
        // 从左边向list中插入数据
        redisTemplate.opsForList().leftPushAll(key,ids.toArray());

        // 取出list中的全部数据
        List<Object> range = redisTemplate.opsForList().range(key, 0, -1);

        // 队尾出队
        Object rightPop = redisTemplate.opsForList().rightPop(key);
        System.out.println("队尾出队"+rightPop);

        // 队首出队
        Object leftPop = redisTemplate.opsForList().leftPop(key);
        System.out.println("队首出队"+leftPop);

        // 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回
        Object rightPopAndLeftPush = redisTemplate.opsForList().rightPopAndLeftPush(key, "list1" + userId);
        System.out.println("新队列元素"+redisTemplate.opsForList().range("list1"+userId,0,-1));

        if (Objects.nonNull(rightPopAndLeftPush)){
            // 只有存在key对应的列表才能将这个value值插入到key所对应的列表中
            redisTemplate.opsForList().leftPushIfPresent("testList",rightPopAndLeftPush);
        }


        // 可利用list数据结构实现队列和栈的功能
        return BaseResponse.successData(range);
    }

    @Override
    public BaseResponse redisSet(String userId) {
        String key = "set"+userId;
        redisTemplate.delete(key);
        ShopInfo shopInfo = new ShopInfo();
        ShopInfo shopInfo1 = new ShopInfo();

        redisTemplate.opsForSet().add(key,shopInfo,shopInfo1);
        return BaseResponse.successData(redisTemplate.opsForSet().members(key));
    }

    @Override
    public BaseResponse redisZSet(String userId) {
        String key = "zSet"+userId;
        ShopInfo third = ids.get(3);
        redisTemplate.delete(key);
        // 向key中新增一个有序集合，存在的话为false，不存在的话为true
        ids.forEach(each->redisTemplate.opsForZSet().add(key, each, each.getShopPrice().doubleValue()));

        // 查询集合中全部数据
        Set<Object> range = redisTemplate.opsForZSet().range(key, 0, -1);
        System.out.println("全部数据："+range);

        // 增加元素的score值，并返回增加后的值
        Double aDouble = redisTemplate.opsForZSet().incrementScore(key, third, 2.0);
        System.out.println("元素新的score:"+aDouble);

        // 从有序集合中移除一个或者多个元素
        Long remove = redisTemplate.opsForZSet().remove(key, third);
        System.out.println("删除元素数量："+remove);

        // 返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
        ShopInfo first = ids.get(0);
        Long rank = redisTemplate.opsForZSet().rank(key, first);

        // 返回有序集中指定成员的排名，其中有序集成员按分数值递减(从大到小)顺序排列
        Long aLong = redisTemplate.opsForZSet().reverseRank(key, first);

        // 计算给定的一个有序集的并集，并存储在新的 destKey中，key相同的话会把score值相加
        redisTemplate.opsForZSet().unionAndStore("test001","test002","test003");
        return BaseResponse.success();
    }

    @Override
    public BaseResponse hotSearchOnWeibo(String key) {

        long times = System.currentTimeMillis() / (60 * 60 * 1000);
        String redisKey = Dict.WEIBO + times;
        redisTemplate.opsForZSet().incrementScore(redisKey,key,1);
        return BaseResponse.success();
    }


}
