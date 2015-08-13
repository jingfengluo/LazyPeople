package com.lazypeople.dao;

import org.springframework.stereotype.Repository;

import com.lazypeople.entity.User;

@Repository("userMapper")
public interface UserMapper {

	int insert(User record);
}