package com.honghao.cloud.accountapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.accountapi.common.dict.Dict;
import com.honghao.cloud.accountapi.common.enums.ErrorCodeEnum;
import com.honghao.cloud.accountapi.component.MessageSender;
import com.honghao.cloud.accountapi.config.RedisConfig;
import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.accountapi.domain.mapper.ShopInfoMapper;
import com.honghao.cloud.accountapi.dto.request.LikePointVO;
import com.honghao.cloud.accountapi.service.RedisService;
import com.honghao.cloud.basic.common.base.BaseAssert;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.bean.CacheTemplate;
import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.honghao.cloud.accountapi.common.dict.CommonMap.HASH_MAP;

/**
 * reids各个数据测试
 *
 * @author chenhonghao
 * @date 2020-07-23 21:31
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1);
    private static final ThreadPoolExecutor POOL_EXECUTOR = ThreadPoolFactory.buildThreadPoolExecutor(1, 10, "add_redis");
    private static final ScheduledThreadPoolExecutor EXECUTOR = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1);
//    private static final ConcurrentHashMap<String, Boolean> MAP = new ConcurrentHashMap<>();
    private static volatile boolean flag = true;
    private static List<ShopInfo> ids = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            String value = String.valueOf(i);
            ids.add(ShopInfo.builder().shopName(value).shopUrl(value).shopId("20200724000000" + i).shopPrice(new BigDecimal(i)).build());
        }
    }

    private ConcurrentHashMap<String, ReentrantLock> concurrentHashMap = new ConcurrentHashMap<>();
    @Resource
    private ShopInfoMapper shopInfoMapper;
    @Resource
    private MessageSender messageSender;
    @Resource
    private CacheTemplate<ShopInfo> cacheTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void timerTask() {
        SCHEDULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(() -> {
            // 利用list数据结构实现队列消费功能
            String key = "list12345";
            Object object;
            while (Objects.nonNull(object = redisTemplate.opsForList().rightPop(key))) {
                System.out.println(object);
            }
            // 刷新每日，每周，每月的热点数据
            long times = System.currentTimeMillis() / (60 * 60 * 1000);
            refreshDay(times);
            refreshWeek(times);
            refreshMonth(times);

        }, 0, 2, TimeUnit.MINUTES);

        // 每分钟往令牌桶里面放入10个令牌
        String key = "tokenBucket";
        EXECUTOR.scheduleAtFixedRate(() -> redisTemplate.execute((RedisCallback<String>) redisConnection -> {
            redisConnection.eval(RedisConfig.TOKEN_BUCKET_CURRENT_LIMIT_RESYNC.getBytes(), ReturnType.VALUE, 1, key.getBytes(), String.valueOf(10).getBytes(), String.valueOf(9).getBytes());
            return null;
        }), 0, 1, TimeUnit.MINUTES);

    }

    @Override
    public BaseResponse addBigData(String userId) {
        POOL_EXECUTOR.execute(() -> redisTemplate.executePipelined((RedisCallback<String>) redisConnection -> {
            for (int i = 0; i < 2000000; i++) {
                String s = String.valueOf(i);
                redisConnection.hSet("123456".getBytes(), s.getBytes(), s.getBytes());
                redisConnection.hSet("1234567".getBytes(), s.getBytes(), s.getBytes());
            }
            redisConnection.close();
            return null;
        }));
        return BaseResponse.success();
    }

    @Override
    public BaseResponse delBigHash(String userId) {
        long start = System.currentTimeMillis();
        long middle = System.currentTimeMillis();
        System.out.println(middle - start);
        // 删除bigHash
        Cursor<Map.Entry<Object, Object>> scan = redisTemplate.opsForHash().scan("1234567", ScanOptions.scanOptions().count(100).build());
        while (scan.hasNext()) {
            Map.Entry<Object, Object> next = scan.next();
            redisTemplate.opsForHash().delete("1234567", next.getKey());
        }

        System.out.println(System.currentTimeMillis() - middle);

        // redis删除List类型的BigKey


        // redis删除Set类型的BigKey
        List<Object> list = new ArrayList<>();
        Cursor<Object> scan1 = redisTemplate.opsForSet().scan("123", ScanOptions.scanOptions().count(100).build());
        while (scan1.hasNext()) {
            Object next = scan1.next();
            if (list.size() >= 100) {
                redisTemplate.executePipelined(new SessionCallback<Object>() {
                    @Override
                    public Object execute(RedisOperations redisOperations) throws DataAccessException {
                        for (Object o : list) {
                            redisOperations.opsForSet().remove("1234", o);
                        }
                        return null;
                    }
                });
                list.clear();
            } else {
                list.add(next);
            }
            redisTemplate.opsForSet().remove("1234", next);
        }

        // redis删除SortedSet类型的BigKey
        Long aLong = redisTemplate.opsForZSet().removeRange("", 1, 100);

        try {
            scan.close();
            scan1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BaseResponse.success();
    }

    /**
     * redis的bigKey访问问题
     *
     * @param userId userId
     * @return BaseResponse
     */
    @Override
    public BaseResponse bigKey(long userId) {
        // 判断对应用户是否已经秒杀过对应商品，防止重复秒杀
        String shopName = "apple";
        Boolean aBoolean1 = redisTemplate.opsForHash().putIfAbsent(shopName, String.valueOf(userId), String.valueOf(userId));
        if (!Objects.equals(aBoolean1, true)) {
            return BaseResponse.error("已经参加过秒杀活动");
        }

        for (int i = 0; i < 98; i++) {
            String key = "shopName-" + i;
            redisTemplate.delete(key);
        }
        // 判断分段之中是否任然存在库存数据
       if (!segmentProcessing(userId,0,shopName)){
           return BaseResponse.error("无库存");
       }
        //入队，进入异步队列,在异步队列中写入用户信息和对应商品ID，因为服务端可以根据用户ID和商品ID定位用户是否秒杀过对应商品，并且服务端需要根据商品ID去查询数据库中对应商品的库存是否足够。
        messageSender.publicQueueProcessing("", "");
        return BaseResponse.success();
    }

    private boolean segmentProcessing(long userId,int times,String shopName){
        ++times;
        boolean flag = true;
        // 对访问的热点key或者bigKey进行切分
        String key = "shopName-" + (userId % 100);
        ConcurrentHashMap<String,Boolean> map = new ConcurrentHashMap<>();
        if (HASH_MAP.putIfAbsent(shopName,map)!=null){
            map = HASH_MAP.get(shopName);
        }

        // 先通过本地缓存判断是否正确，本地都失败的话，没必要访问redis
        Boolean aBoolean = map.putIfAbsent(key, true);
        if (Objects.equals(aBoolean, false)) {
            flag = false;
        }
        if (flag){
            // 从Redis缓存中进行预减库存
            Long decrement = redisTemplate.opsForValue().decrement(key);
            if (decrement == null) {
                return false;
            }
            // 预减库存时库存不足，当前段的库存不足时，自动切换到下一段进行重试处理
            if (decrement < 0) {
                // 一旦商品库存没有了，设置对应商品内存标志为false
                map.put(key, false);
                flag = false;
            }
        }
        // 通过递归访问，遍历所有(当某一个成功或者全部失败时，退出递归)
        // 自动切换到下一段进行重试处理,直到成功或者全部失败，表示全部库存为0，但是最好不要直接访问redis，
        // 如果遍历100个key,严重影响性能,可以设置一个阈值，如果访问了5个段，任然无库存，就认为当前用户访问时确实无库存了
        if (!flag && times <= 5){
            flag = segmentProcessing((userId % 100) + 1,times,shopName);
        }
        return flag;
    }

    @Override
    public BaseResponse cacheBreakdown(String userId) {
        Object o = redisTemplate.opsForValue().get(userId);
        if (Objects.nonNull(o)) {
            return BaseResponse.success();
        }
        // 缓存未命中时，以用户id为颗粒度加锁，加本地锁，减少访问数据库的次数（如要完全限制只查询一次数据库，使用分布式锁）
        ReentrantLock reentrantLock = new ReentrantLock();
        if ((concurrentHashMap.putIfAbsent(userId, reentrantLock)) != null) {
            reentrantLock = concurrentHashMap.get(userId);
        }

        reentrantLock.lock();
        try {
            // 利用DCL(Double Check Lock)双锁检查机制
            Object temp = redisTemplate.opsForValue().get(userId);
            if (Objects.nonNull(temp)) {
                return BaseResponse.success();
            }
            // 查询数据库并且放入到缓存中
            ShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(userId);
            redisTemplate.opsForValue().set(userId, shopInfo);
            return BaseResponse.success();
        } finally {
            // 当前锁的等待队列为空时，删除该锁
            if (!reentrantLock.hasQueuedThreads()) {
                concurrentHashMap.remove(userId);
            }
            reentrantLock.unlock();
        }
    }

    /**
     * 利用redis中的bitmap实现redis布隆过滤器
     *
     * @param userId userId
     * @return BaseResponse
     */
    @Override
    public BaseResponse cachePenetration(String userId) {
        return cacheTemplate.redisStringCache(null, userId, 10, TimeUnit.MINUTES, () -> shopInfoMapper.selectByPrimaryKey("1"));
    }

    @Override
    public BaseResponse redisLock(String userId) {
        String clientId = UUID.randomUUID().toString();
        try {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(userId, clientId, 1000, TimeUnit.SECONDS);
            BaseAssert.notNull(aBoolean, "");
            if (!aBoolean) {
                return BaseResponse.error(ErrorCodeEnum.API_GATEWAY_ERROR);
            }
            // 业务处理
        } finally {
            /*
             * 如果查询后确实相等，但是此时该key已经过期，别的线程已经成功加锁后，在删除时会出现误删除的情况.
             * 所以这里必须通过执行lua语句或脚本来执行，在eval命令执行Lua代码的时候，Lua代码将被当成一个命令去执行，并且直到eval命令执行完成，Redis才会执行其他命令。
             */
//            if (clientId.equals(redisTemplate.opsForValue().get(userId))) {
//                redisTemplate.delete(userId);
//            }
            // 优化为如下代码，利用lua来确保查询和删除直减不会被插入其他redis操作
            String client = "\"" + clientId + "\"";
            redisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.eval(RedisConfig.UNLOCK.getBytes(), ReturnType.VALUE, 1, userId.getBytes(), client.getBytes()));
        }
        return BaseResponse.success();
    }


    @Override
    public BaseResponse redisList(String userId) {
        String key = "list" + userId;
        redisTemplate.delete(key);
        // 从左边向list中插入数据
        redisTemplate.opsForList().leftPushAll(key, ids.toArray());

        // 取出list中的全部数据
        List<Object> range = redisTemplate.opsForList().range(key, 0, -1);

        // 队尾出队
        Object rightPop = redisTemplate.opsForList().rightPop(key);
        System.out.println("队尾出队" + rightPop);

        // 队首出队
        Object leftPop = redisTemplate.opsForList().leftPop(key);
        System.out.println("队首出队" + leftPop);

        // 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回
        Object rightPopAndLeftPush = redisTemplate.opsForList().rightPopAndLeftPush(key, "list1" + userId);
        System.out.println("新队列元素" + redisTemplate.opsForList().range("list1" + userId, 0, -1));

        if (Objects.nonNull(rightPopAndLeftPush)) {
            // 只有存在key对应的列表才能将这个value值插入到key所对应的列表中
            redisTemplate.opsForList().leftPushIfPresent("testList", rightPopAndLeftPush);
        }

        // 可利用list数据结构实现队列和栈的功能
        return BaseResponse.successData(range);
    }

    @Override
    public BaseResponse redisSet(String userId) {
        String key = "set" + userId;
        redisTemplate.delete(key);
        ShopInfo shopInfo = new ShopInfo();
        ShopInfo shopInfo1 = new ShopInfo();
        Long add = redisTemplate.opsForSet().add(key, shopInfo, shopInfo1);
        redisTemplate.opsForValue().setBit(key, 123L, true);
        System.out.println(add);
        return BaseResponse.successData(redisTemplate.opsForSet().members(key));
    }

    @Override
    public BaseResponse redisSortedSet(String userId) {
        final String key1 = "order:time:out";
        long l = System.currentTimeMillis() / 1000 + 60;
        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForZSet().add(key1, i, l);
        }
        Set<ZSetOperations.TypedTuple<Object>> set1 = redisTemplate.opsForZSet().rangeWithScores(key1, 0, 1);
        assert set1 != null;
        for (ZSetOperations.TypedTuple<Object> tuple : set1) {
            redisTemplate.opsForZSet().remove(key1, tuple.getValue());
        }

        String key = "zSet" + userId;
        ShopInfo third = ids.get(3);
        redisTemplate.delete(key);
        // 向key中新增一个有序集合，存在的话为false，不存在的话为true
        ids.forEach(each -> redisTemplate.opsForZSet().add(key, each, each.getShopPrice().doubleValue()));

        // 查询集合中全部数据
        Set<Object> range = redisTemplate.opsForZSet().range(key, 0, -1);
        System.out.println("全部数据：" + range);

        // 增加元素的score值，并返回增加后的值
        Double aDouble = redisTemplate.opsForZSet().incrementScore(key, third, 2.0);
        System.out.println("元素新的score:" + aDouble);

        // 从有序集合中移除一个或者多个元素
        Long remove = redisTemplate.opsForZSet().remove(key, third);
        System.out.println("删除元素数量：" + remove);

        // 返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
        ShopInfo first = ids.get(0);
        Long rank = redisTemplate.opsForZSet().rank(key, first);
        System.out.println(rank);

        // 返回有序集中指定成员的排名，其中有序集成员按分数值递减(从大到小)顺序排列
        Long aLong = redisTemplate.opsForZSet().reverseRank(key, first);
        System.out.println(aLong);

        // 查询zset集合中所有数据，并且按照从大到小的排序规则
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
        System.out.println(set);

        // 计算给定的一个有序集的并集，并存储在新的 destKey中，key相同的话会把score值相加
        redisTemplate.opsForZSet().unionAndStore("test001", "test002", "test003");
        return BaseResponse.success();
    }

    @Override
    public BaseResponse redisHyperLogLog(String userId) {
        String key = "redisHyperLogLog";
        // 添加元素
        redisTemplate.opsForHyperLogLog().add(key, userId);

        Long size = redisTemplate.opsForHyperLogLog().size(key);
        System.out.println(size);

        return BaseResponse.success();
    }

    @Override
    public BaseResponse redisGeo(String userId) {
        Map<Object, Point> map = new HashMap<>(16);
        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), new Point(123, 1.34));
        }
        Point point = new Point(123, 1.34);
        redisTemplate.opsForGeo().add("1234", point, "123");
        redisTemplate.opsForGeo().add("1234", map);
        redisTemplate.opsForGeo().remove("1234", "1", "2");
        return BaseResponse.success();
    }

    @Override
    public BaseResponse redisInfo(String userId) {
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        BaseAssert.notNull(factory, "");
        RedisConnection conn = null;

        try {
            conn = RedisConnectionUtils.getConnection(factory);
            Long incr = conn.incr(userId.getBytes());
            Map redisInfo = conn.info();
            BaseAssert.notNull(redisInfo, "");

            JSONObject json = new JSONObject();
            json.put("incr", incr);
            json.put("processId", redisInfo.get("process_id"));
            System.out.println(JSON.toJSONString(json));
            return BaseResponse.successData(JSON.toJSONString(json));
        } finally {
            RedisConnectionUtils.releaseConnection(conn, factory);
        }
    }

    @Override
    public BaseResponse hotSearchOnWeibo(String key) {
        long times = System.currentTimeMillis() / (60 * 60 * 1000);
        String redisKey = Dict.WEIBO + times;
        redisTemplate.opsForZSet().incrementScore(redisKey, key, 1);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse getHot(String type) {
        if ("hour".equals(type)) {
            long times = System.currentTimeMillis() / (60 * 60 * 1000);
            type = Dict.WEIBO + times;
        }
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeWithScores(type, 0, -1);
        return BaseResponse.successData(set);
    }

    @Override
    public BaseResponse likePoint(LikePointVO data) {
        try {
            redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
                redisConnection.setBit((Dict.BIT_MAP + data.getId()).getBytes(), data.getUserId(), data.getStatus());
                redisConnection.expire((Dict.BIT_MAP + data.getId()).getBytes(), 1000 * 60 * 60);
                return null;
            });
        } catch (Exception e) {
            log.error(e.getMessage());
            return BaseResponse.error(e.getMessage());
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse likePointCount(LikePointVO likePointVO) {
        Object execute = redisTemplate.execute((RedisCallback<Object>) redisConnection -> redisConnection.bitCount((Dict.BIT_MAP + likePointVO.getId()).getBytes()));
        return BaseResponse.successData((Long) execute);
    }

    @Override
    public BaseResponse isLikePoint(LikePointVO likePointVO) {
        Boolean bit = redisTemplate.opsForValue().getBit(Dict.BIT_MAP + likePointVO.getId(), likePointVO.getUserId());
        return BaseResponse.successData(bit);
    }

    @Override
    public BaseResponse pubAndSub(String userId) {
        Boolean aBoolean = redisTemplate.opsForValue().setBit(userId, 123L, true);
        if (aBoolean != null && aBoolean) {
            return BaseResponse.error();
        } else {
            return BaseResponse.success();
        }
    }

    @Override
    public BaseResponse redisConcurrent(String userId) {
        if (flag) {
            Long decrement = redisTemplate.opsForValue().increment(userId);
            if (decrement != null && decrement <= 100) {
                System.out.println("123");
                return BaseResponse.success();
            } else {
                System.out.println("error");
                flag = false;
                return BaseResponse.error();
            }
        }
        System.out.println("skip");
        return BaseResponse.error();
    }

    @Override
    public Properties info(String param) {
        if (StringUtils.isEmpty(param)) {
            return redisTemplate.getRequiredConnectionFactory().getConnection().info();
        }
        return redisTemplate.getRequiredConnectionFactory().getConnection().info(param);
    }

    /**
     * 问题存在：当处于高并发环境下时，如果zCard()返回确实小于指定阈值，但是其他服务已经进行了add()操作
     * 已经达到了指定阈值，但是本服务认为还未达到阈值，会继续进行add()操作，这会导致超过指定的阈值
     * <p>
     * 优化策略1：加锁操作，虽然可以确保不会超过阈值，但是会降低并发能力，根据具体的业务来选择即可
     * 优化策略2：判断部分使用lua脚本来实现，保证原子性
     *
     * @param param param
     * @return BaseResponse
     */
    @Override
    public BaseResponse slidingWindowCounter(String param) {
        // 加锁操作
        String key = "slidingWindowCounter";
        String uuid = UUID.randomUUID().toString();
        try {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, uuid, 2, TimeUnit.SECONDS);
            if (Objects.nonNull(aBoolean) && aBoolean) {
                ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

                // 每次正式计数之前，先清除一次过期的数据,当前时间秒数 - 60 = 过期开始时间
                long l = System.currentTimeMillis() / 1000;
                zSetOperations.removeRangeByScore(key, 0, l - 60);
                // 统计当前key中元素个数，即过期一分钟内的请求次数，如果达到阈值，返回请求失败，提示错误信息
                Long aLong = zSetOperations.zCard(key);
                if (Objects.nonNull(aLong) && aLong >= 2) {
                    return BaseResponse.error("达到请求阈值阀门,请稍后重试");
                }
                // 说明此时未达到请求阈值阀门，可以继续接收请求进行业务逻辑处理
                zSetOperations.add(key, param, l);
                // 设置该key的过期时间为单位时间
                redisTemplate.expire(key, 60, TimeUnit.SECONDS);
            }
        } finally {
            String client = "\"" + uuid + "\"";
            redisTemplate.execute((RedisCallback<Integer>) redisConnection -> redisConnection.eval(RedisConfig.UNLOCK.getBytes(), ReturnType.VALUE, 1, key.getBytes(), client.getBytes()));
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse slidingWindowCounterUpdate(String param) {
        // 加锁操作
        String key = "slidingWindowCounter";
        String value = UUID.randomUUID().toString();
        long l = System.currentTimeMillis() / 1000;
        Long execute1 = redisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.eval(RedisConfig.SLIDING_WINDOW_CURRENT_LIMIT.getBytes(), ReturnType.VALUE, 1, key.getBytes(), String.valueOf(l - 60).getBytes(), String.valueOf(2).getBytes(), String.valueOf(l).getBytes(), value.getBytes()));

        if (Objects.equals(0L, execute1)) {
            return BaseResponse.error("达到请求阈值阀门,请稍后重试");
        }
        // 做业务逻辑操作
        return BaseResponse.success();
    }

    @Override
    public BaseResponse tokenBucket(String param) {
        String key = "tokenBucket";
        Object o = redisTemplate.opsForList().leftPop(key);
        if (Objects.isNull(o)) {
            return BaseResponse.error("达到请求阈值阀门,请稍后重试");
        }
        System.out.println("消费的当前token值为：" + o);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse leakyBucket(String param) {
        String key = "leakyBucket";
        Object o = redisTemplate.opsForList().rightPush(key, UUID.randomUUID().toString());
        if (Objects.isNull(o)) {
            return BaseResponse.error("达到请求阈值阀门,请稍后重试");
        }
        System.out.println("消费的当前token值为：" + o);
        return BaseResponse.success();
    }


    private void refreshDay(Long time) {
        // 获取前23小时的数据和当前时间数据，刷新到
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 24; i++) {
            list.add(Dict.WEIBO + (time - i));
        }
        redisTemplate.opsForZSet().unionAndStore(Dict.WEIBO + time, list, Dict.WEIBO_DAY);

        list.add(Dict.WEIBO + time);
        for (String s : list) {
            redisTemplate.expire(s, 40, TimeUnit.DAYS);
        }
    }

    private void refreshWeek(Long time) {
        // 获取一周的数据和当前时间数据，刷新到
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 24 * 7; i++) {
            list.add(Dict.WEIBO + (time - i));
        }
        redisTemplate.opsForZSet().unionAndStore(Dict.WEIBO + time, list, Dict.WEIBO_WEEK);
    }

    private void refreshMonth(Long time) {
        // 获取一月的数据和当前时间数据，刷新到
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 24 * 7 * 30; i++) {
            list.add(Dict.WEIBO + (time - i));
        }
        redisTemplate.opsForZSet().unionAndStore(Dict.WEIBO + time, list, Dict.WEIBO_MONTH);
    }

    public void test(String shipNo, String company) {
        LocalDate localDate = LocalDate.now();
        String k = shipNo + company;
        // 基于当前业务量，基本稳定在700w左右，预留到1000w容量，分为2500个key,保持每隔key中数量不超过5000个
        int index = (k).hashCode() % 2500;

        // 防止跨天重复，需要对比今天和昨天的数据
        String todayKey = "Ship:Id" + localDate.format(FORMATTER) + index;
        String yesKey = "Ship:Id" + localDate.minusDays(1).format(FORMATTER) + index;

        // 只有两个hash结构中都不存在该key时，才会认为是初次处理，否则已经处理过了，直接返回，保证幂等性
        Object o = redisTemplate.opsForHash().get(yesKey, k);
        Boolean today = redisTemplate.opsForHash().putIfAbsent(todayKey, k, k);

        Random random = new Random();
        // 需要为key设置过期时间，而且要尽量分散开来，避免key集中过期，造成阻塞,设置过期时间为24到26个小时
        redisTemplate.expire(todayKey, random.nextInt(60) + 24 * 60, TimeUnit.MINUTES);

        if (Objects.isNull(o) && today) {
            System.out.println("执行业务逻辑");
        }
    }
}
