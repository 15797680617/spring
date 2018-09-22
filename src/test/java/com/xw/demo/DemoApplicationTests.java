package com.xw.demo;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xw.demo.config.JedisConfig;
import com.xw.demo.dao.UserMapper;
import com.xw.demo.entity.SfUser;
import com.xw.demo.utils.JedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
public class DemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JedisConfig jedisConfig;

	@Test
	public void contextLoads() {
		List<SfUser> list = userMapper.getAllUser();
		List<SfUser> newList = new ArrayList<>();
		Jedis jedis = new Jedis("192.168.75.128",6379);
		for (SfUser user : list) {
			//jedis.set("userCode_"+user.getUserCode(),JSON.toJSONString(user));
			// 从redis中 根据用户code 查询出用户对象json字符串 转换成对象
			String userStr = jedis.get("userCode_" + user.getUserCode());
			SfUser user1 =  JSON.parseObject(userStr,SfUser.class);
			newList.add(user1);
		}
		jedis.close();

		for (SfUser user : newList) {
			System.out.println(user.toString());
		}
	}

	@Test
	public void test1(){
		Jedis jedis = JedisUtils.getJedis();
		System.out.println(jedis.ping());
		jedis.close();
		System.out.println(jedis);
	}

	@Test
	public void fun1() throws Exception {
		OutputStream out = new FileOutputStream("d://sfUser.xls");
		List<SfUser> list = userMapper.getAllUser();
		List<String> names = new ArrayList<>();
		for (SfUser user : list) {
			names.add(user.getUserCode());
			names.add(user.getUserName());
			names.add(user.getUserTruename());
		}
		List<List<String>> lists = new ArrayList<>();
		ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS);
		Sheet sheet = new Sheet(1, 0);
		writer.write0(lists, sheet);
		writer.finish();
		out.close();

	}

}
