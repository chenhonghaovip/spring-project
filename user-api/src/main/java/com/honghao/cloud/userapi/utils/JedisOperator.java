package com.honghao.cloud.userapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * redis 基本操作
 * 
 * @author wsfeng
 */
@Slf4j
@Component
public class JedisOperator {
	@Resource
	@Qualifier("spring.redis.pool")
	private JedisPool jedisPool;

	private Jedis getResource() {
		return jedisPool.getResource();
	}

	private void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	public void expire(String key, Integer seconds) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			log.error("Redis expire error: " + e.getMessage() + " - " + key + ", time = " + seconds);
		} finally {
			returnResource(jedis);
		}
	}

	public void pexpireAt(String key, Long millisecondsTimestamp) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.pexpireAt(key, millisecondsTimestamp);
		} catch (Exception e) {
			log.error("Redis expireAt error: " + e.getMessage() + " - " + key + ", time = " + millisecondsTimestamp);
		} finally {
			returnResource(jedis);
		}
	}

	public void psetex(String key, String value, Long millisecondsTimestamp) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.psetex(key, millisecondsTimestamp, value);
		} catch (Exception e) {
			log.error("Redis psetex error: " + e.getMessage() + " - " + key + ", time = " + millisecondsTimestamp);
		} finally {
			returnResource(jedis);
		}
	}

	public void remove(String key) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.expire(key, 0);
		} catch (Exception e) {
			log.error("Redis remove error: " + e.getMessage() + " - " + key);
		} finally {
			returnResource(jedis);
		}
	}

	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			log.error("Redis set error: " + e.getMessage() + " - " + key + ", value:" + value);
		} finally {
			returnResource(jedis);
		}
	}

	public String setnxex(String key, String value, Integer seconds) {
		Jedis jedis = null;
		String result ;
		try {
			jedis = getResource();
			result = jedis.set(key, value, "NX", "EX", seconds);
		} catch (Exception e) {
			log.error(
					"Redis set error: " + e.getMessage() + " - " + key + ", time = " + seconds + ", value:" + value);
			return "ok";
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	public void setex(String key, String value, Integer seconds) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.setex(key, seconds, value);
			log.debug("Redis set success - " + key + ", time = " + seconds + ", value:" + value);
		} catch (Exception e) {
			log.error(
					"Redis set error: " + e.getMessage() + " - " + key + ", time = " + seconds + ", value:" + value);
		} finally {
			returnResource(jedis);
		}
	}

	public String get(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.get(key);
		} catch (Exception e) {
			log.error("Redis set error: " + e.getMessage() + " - " + key + ", value:" + result);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	public String getString(String key) {
		String result = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.get(key);
			if (result == null) {
				return "";
			}
		} catch (Exception e) {
			log.error("Redis set error: " + e.getMessage() + " - " + key + ", value:" + result);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	public Map<String, String> HGETALL(String key) {
		Map<String, String> map = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			Pipeline pipeline = jedis.pipelined();
			Response<Map<String, String>> response = pipeline.hgetAll(key);
			pipeline.sync();
			map = response.get();
		} catch (Exception e) {
			log.error("从Jedis获取HGETALL值出现异常：", e);
		} finally {
			returnResource(jedis);
		}
		return map;
	}

	public Map<String, String> hgetAll(byte[] bytes) {
		Map<String, String> hashMap = null;
		Jedis jedis = null;
		try {
			hashMap = new HashMap<>();
			Map<byte[], byte[]> map = null;
			jedis = getResource();
			Pipeline pipeline = jedis.pipelined();
			Response<Map<byte[], byte[]>> response = pipeline.hgetAll(bytes);
			// Response<Map<String, String>> response = pipeline.hgetAll(key);
			pipeline.sync();
			map = response.get();
			for (Entry<byte[], byte[]> entry : map.entrySet()) {
				hashMap.put(new String(entry.getKey()), new String(entry.getValue()));
			}
		} catch (Exception e) {
			log.error("从Jedis获取HGETALL值出现异常：", e);
		} finally {
			returnResource(jedis);
		}
		return hashMap;
	}

	/**
	 * 删除哈希表 key中的一个或多个指定域。
	 * 
	 * @param key ket
	 * @param field values
	 */
	public void HDEL(String key, String... field) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hdel(key, field);
		} catch (JedisException e) {
			log.error("Redis HDEL has error：", e);
		} finally {
			returnResource(jedis);
		}
	}

	public void HDEL(byte[] bytes, byte[]... fields) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hdel(bytes, fields);
		} catch (JedisException e) {
			log.error("Redis HDEL has error：", e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 由jedis代理，向redis缓存放数据
	 * 
	 * @param key
	 * @param value
	 * @throws JedisException
	 */
	public void HSET(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hset(key, field, value);
		} catch (JedisException e) {
			log.error("Redis HSET has error：", e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 由jedis代理，向redis缓存取数据
	 * 
	 * @param key key
	 * @param field value
	 * @throws JedisException
	 */
	public String HGET(String key, String field) {
		Jedis jedis = null;
		String obj = null;
		try {
			jedis = getResource();
			obj = jedis.hget(key, field);
		} catch (JedisException e) {
			log.error("Redis HGET has error：", e);
		} finally {
			returnResource(jedis);
		}
		return obj;
	}

	public byte[] hget(byte[] key, byte[] field) {
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = this.getResource();
			result = jedis.hget(key, field);
		} catch (Exception e) {
			log.error("Redis set error: " + e.getMessage() + " - " + key + ", value:" + result);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	public void hset(byte[] key, byte[] field, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.hset(key, field, value);
		} catch (JedisException e) {
			log.error("Redis HSET has error：", e);
		} finally {
			returnResource(jedis);
		}
	}

	public String setNx(String lockName, long acquireTimeMs, long timeOutMs) {
		Jedis jedis = null;
		String identifer;
		String retIdentifier = null;
		try {
			jedis = getResource();
			identifer = UUID.randomUUID().toString();
			String lockKey = "lock:" + lockName;
			// 超时时间
			int lockExpire = (int) (timeOutMs / 1000);
			// 获取锁的超时时间
			long end = System.currentTimeMillis() + acquireTimeMs;
			while (System.currentTimeMillis() < end) {
				if (jedis.setnx(lockKey, identifer) == 1) {
					jedis.expire(lockKey, lockExpire);
					retIdentifier = identifer;
					return retIdentifier;
				}
				// 返回-1代表key没有设置超时时间，为key设置一个超时时间
				if (jedis.ttl(lockKey) == -1) {
					jedis.expire(lockKey, lockExpire);
				}
			}
		} catch (Exception e) {
			log.debug("Redis setNx error :  " + e.getMessage() + " key : " + lockName);
		} finally {
			returnResource(jedis);
		}
		return retIdentifier;
	}

	public boolean releaseLock(String lockName, String identifier) {
		Jedis jedis = null;
		String lockKey = "lock:" + lockName;
		boolean retFlag = false;
		try {
			jedis = jedisPool.getResource();
			while (true) {
				// 监视lock，准备开始事务
				jedis.watch(lockKey);
				// 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
				if (identifier.equals(jedis.get(lockKey))) {
					Transaction transaction = jedis.multi();
					transaction.del(lockKey);
					List<Object> results = transaction.exec();
					if (results == null) {
						continue;
					}
					retFlag = true;
				}
				jedis.unwatch();
				break;
			}
		} catch (JedisException e) {
			log.debug("Redis releaseLock error : key = " + lockKey + " value = " + identifier);
		} finally {
			this.returnResource(jedis);
		}
		return retFlag;
	}

	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.del(key);
		} catch (Exception e) {
			log.error("Redis del error: " + e.getMessage());
		} finally {
			returnResource(jedis);
		}
	}

	public void del(byte[] bytes) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.del(bytes);
		} catch (Exception e) {
			log.error("Redis del error: " + e.getMessage());
		} finally {
			returnResource(jedis);
		}
	}

	public long setnx(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			return jedis.setnx(key, value);
		} catch (Exception e) {
			log.error("Redis setnx error: " + e.getMessage() + " - " + key + ", value:" + value);
		} finally {
			returnResource(jedis);
		}
		return 0;
	}

	public long ttl(String key) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			return jedis.ttl(key);
		} catch (Exception e) {
			log.error("Redis TTL has error：", e);
		} finally {
			returnResource(jedis);
		}
		return 0L;
	}

	public boolean exists(String key) {
		Jedis jedis = null;
		boolean flag = false;
		try {
			jedis = getResource();
			flag = jedis.exists(key);
		} catch (Exception e) {
			log.error("从Jedis获取exists值出现异常：", e);
		} finally {
			returnResource(jedis);
		}
		return flag;
	}

	/**
	 * 自增长
	 * 
	 * @param key
	 * @return
	 */
	public Long incr(String key) {
		Jedis jedis = null;
		long value = 10000000;
		try {
			jedis = getResource();
			value = jedis.incr(key);
		} catch (Exception e) {
			log.error("从Jedis自增长值出现异常：", e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 自减
	 * 
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		long value = 0L;
		try (Jedis jedis = getResource()) {
			value = jedis.decr(key);
		} catch (Exception e) {
			log.error("从Jedis获取自减值出现异常：", e);
		}
		return value;
	}

	public String setNx(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.setnx(key, value);
		} catch (Exception e) {
			log.error("操作失败");
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * redis锁
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            秒
	 * @return
	 */
	public String setNxEx(String key, String value, long time) {
		String set = null;
		try (Jedis jedis = getResource()) {
			set = jedis.set(key, value, "NX", "EX", time);
		} catch (Exception e) {
			log.error("操作失败:", e);
		}
		return set;
	}

	/**
	 * hash 自增长
	 * 
	 * @param key
	 * @param field
	 * @param scale
	 *            步长
	 * @return
	 */
	public long hincr(String key, String field, long scale) {
		long hincrBy;
		try (Jedis jedis = getResource()) {
			hincrBy = jedis.hincrBy(key, field, scale);
		} catch (Exception e) {
			log.error("操作失败:", e);
			hincrBy = 0L;
		}
		return hincrBy;
	}

	/**
	 * hash 自增长
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public long hincr(String key, String field) {
		return hincr(key, field, 1L);
	}

	/**
	 * 添加geo
	 * 
	 * @param key
	 * @param longitude
	 * @param latitude
	 * @param dName
	 *            位置名称
	 * @return
	 */
	public Long geoAdd(String key, String longitude, String latitude, String dName) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			return (Long) jedis.eval("return redis.call('GEOADD',KEYS[1],KEYS[2],KEYS[3],KEYS[4])", 4, key, longitude,
					latitude, dName);
		} catch (Exception e) {
			log.error("Redis geoADD error: " + e.getMessage());
		} finally {
			returnResource(jedis);
		}
		return 0L;
	}

}
