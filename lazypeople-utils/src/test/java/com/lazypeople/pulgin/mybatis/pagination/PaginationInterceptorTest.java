package com.lazypeople.pulgin.mybatis.pagination;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lazypeople.pulgin.mybatis.pagination.mock.User;
import com.lazypeople.pulgin.mybatis.pagination.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PaginationInterceptorTest {
	
	@Resource
	@Qualifier("userMapper")
	private UserMapper mapper;
	
	@Test
	public void testSave() {
		User u = new User();
		u.setId("1");
		u.setName("test");
		
	}
	
	@Test
	public void testByPageView() {
		Page page = new Page();
		User u = new User();
		u.setId("1");
		List<User> items = mapper.listByPage(u, page);
		System.out.println(items.size());
	}


}
