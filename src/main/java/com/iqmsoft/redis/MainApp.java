package com.iqmsoft.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableCaching
public class MainApp {

	@Value("${spring.redis.host}")
	private String REDIS_HOST;
	@Value("${spring.redis.port}")
	private int REDIS_PORT;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(REDIS_HOST);
		factory.setPort(REDIS_PORT);
		factory.setUsePool(true);
		return factory;
	}

	@Bean("cacheRedisTemplate")
	public RedisTemplate<Integer, Integer> cacheRedisTemplate() {
		RedisTemplate<Integer, Integer> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager(@Qualifier("cacheRedisTemplate") RedisTemplate<Integer, Integer> template) {
		RedisCacheManager cacheManager = new RedisCacheManager(template);
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}

	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}
}
