package com.lazypeople.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lazypeople.entity.User;
import com.lazypeople.service.UserService;

@Controller
public class LoginController extends BaseController{

	@Resource(name = "userService")
	UserService userService;

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public User login(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpServletRequest request, HttpSession session) {
		System.out.println(username);
		return null;
	}

}
