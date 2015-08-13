package com.lazypeople.pulgin.mybatis.pagination.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lazypeople.pulgin.mybatis.pagination.Page;
import com.lazypeople.pulgin.mybatis.pagination.mock.User;

@Repository("userMapper")
public interface UserMapper {
	
	List<User> listByPage(@Param("user") User user, Page page);
	
}
