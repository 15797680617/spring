package com.xw.demo.listeners;

import com.xw.demo.config.JedisConfig;
import com.xw.demo.dao.UserMapper;
import com.xw.demo.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class AppListeners implements CommandLineRunner {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisConfig jedisConfig;


	@Override
	public void run(String... args) throws Exception {
		System.err.println("系统启动中...");
		System.err.println("正在配置redis连接池...");
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(jedisConfig.getMaxIdle());
		JedisUtils.POOL = new JedisPool(jedisConfig.getHost(), jedisConfig.getPort());
	}
}
