package com.xw.demo.dao;

import com.xw.demo.entity.SfUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

	@Select("select * from sf_user")
	List<SfUser> getAllUser();
}
