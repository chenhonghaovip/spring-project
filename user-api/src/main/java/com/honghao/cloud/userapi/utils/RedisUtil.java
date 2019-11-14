package com.honghao.cloud.userapi.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author chenhonghao
 * @date 2019-11-13 10:33
 */
public class RedisUtil {

    private static JedisPool jedisPool;

    @Resource
    @Qualifier("spring.redis.pool")
    public static void setJedisPool(JedisPool jedisPool) {
        RedisUtil.jedisPool = jedisPool;
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void close(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 增加地理位置的坐标
     * @param key
     * @param coordinate
     * @param memberName
     * @return
     */
    public static Long geoadd(String key, GeoCoordinate coordinate, String memberName) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.geoadd(key, coordinate.getLongitude(), coordinate.getLatitude(), memberName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 批量添加地理位置
     * @param key
     * @param memberCoordinateMap
     * @return
     */
    public static Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap){
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.geoadd(key, memberCoordinateMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 根据给定地理位置坐标获取指定范围内的地理位置集合（返回匹配位置的经纬度 + 匹配位置与给定地理位置的距离 + 从近到远排序，）
     * @param key
     * @param coordinate
     * @param radius
     * @return  List<GeoRadiusResponse>
     */
    public static List<GeoRadiusResponse> geoRadius(String key, GeoCoordinate coordinate, double radius) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.georadius(key, coordinate.getLongitude(), coordinate.getLatitude(), radius, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortAscending());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 根据给定地理位置获取指定范围内的地理位置集合（返回匹配位置的经纬度 + 匹配位置与给定地理位置的距离 + 从近到远排序，）
     * @param key
     * @param member
     * @param radius
     * @return  List<GeoRadiusResponse>
     */
    List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius){
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.georadiusByMember(key, member, radius, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortAscending());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * 查询两位置距离
     * @param key
     * @param member1
     * @param member2
     * @param unit
     * @return
     */
    public static Double geoDist(String key, String member1, String member2, GeoUnit unit){
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.geodist(key, member1, member2, unit);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 可以获取某个地理位置的geohash值
     * @param key
     * @param members
     * @return
     */
    public static List<String> geohash(String key, String... members){
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.geohash(key, members);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 获取地理位置的坐标
     * @param key
     * @param members
     * @return
     */
    public static List<GeoCoordinate> geopos(String key, String... members){
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.geopos(key, members);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
