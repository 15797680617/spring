package com.xw.demo.controller;

import com.xw.demo.dao.UserMapper;
import com.xw.demo.entity.SfUser;
import com.xw.demo.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.List;

@Controller
public class UserController {
	@Autowired
	private UserMapper userMapper;

	@Bean
	@ConditionalOnMissingBean
	public SfUser getSfUser(){
		return new SfUser();
	}

	@RequestMapping("/getUser")
	@ResponseBody
	public Object getUser(){
		//List<SfUser> list = userMapper.getAllUser();
		Jedis jedis = JedisUtils.getJedis();
		String name = jedis.get("name");
		jedis.close();
		return name;
	}

	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		SfUser user = new SfUser();
		user.setUserTruename("123");
		SfUser sfUser = getSfUser();
		return sfUser.getUserName();
	}

}
