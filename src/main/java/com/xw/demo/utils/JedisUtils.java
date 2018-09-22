package com.xw.demo.utils;

import com.xw.demo.config.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 操作redis的工具类
 */
@Component
public class JedisUtils {
	@Autowired
	private static JedisConfig jedisConfig;

	public static JedisPool POOL;

	public static Jedis getJedis(){
		return POOL.getResource();
	}
}
