package com.honghao.cloud.userapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author chenhonghao
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private  String host;
    @Value("${spring.redis.password}")
    private  String password;
    @Value("${spring.redis.port}")
    private  int port;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;
	@Value("${spring.redis.pool.max-active}")
	private int maxActive;


	@Bean(name = "spring.redis.pool")
	public JedisPool jedisPool(@Qualifier("spring.redis.pool.config") JedisPoolConfig config) {
		JedisPool pool = new JedisPool(config, host, port);
		log.info("init redis...");
		return pool;
	}

	@Bean(name = "spring.redis.pool.config")
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		log.info("init redis config...");
		config.setMaxTotal(maxActive);
		config.setMaxIdle(maxIdle);
		//设置获取连接池超时时间
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}
}
