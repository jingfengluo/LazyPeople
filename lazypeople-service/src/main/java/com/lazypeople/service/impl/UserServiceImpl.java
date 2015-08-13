package com.lazypeople.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lazypeople.entity.User;
import com.lazypeople.service.UserService;
import com.lazypeople.dao.UserMapper;

/**
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource@Qualifier("userMapper") 
    private UserMapper userMapper;

	@Override
	public void save(User user) {
		userMapper.insert(user);
	}
	
}
