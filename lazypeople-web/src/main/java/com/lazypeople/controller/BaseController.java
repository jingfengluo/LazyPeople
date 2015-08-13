package com.lazypeople.controller;

import javax.servlet.http.HttpSession;

import com.lazypeople.entity.User;
import com.lazypeople.web.exception.ValidationException;

/**
 * 公用方法
 * 
 * @author joel
 *
 */
public class BaseController {
	
	public final static String USER_SESSION_KEY = "user"; 
	
	public final static String SESSION_EXPIRE_MSG = "用户登录已过期，请重新登陆!";
	
	public boolean checkUserSession(HttpSession session){
		return getUser(session) != null;
	}
	
	/**
	 * 获取用户SESSION
	 * @param session
	 * @return
	 */
	public User getUser(HttpSession session) {
		if(session != null){
			User manager = (User) session.getAttribute(USER_SESSION_KEY);
			if (manager == null || manager.getId() == null) {
				throw new ValidationException(SESSION_EXPIRE_MSG);
			}
			return manager;
		}
		return null;
	}
	
	/**
	 * 重新放置用户SESSION
	 * @param session
	 * @param user
	 */
	public void resetUserInfo(HttpSession session, User user){
		if(user!=null){
			session.setAttribute(USER_SESSION_KEY, user);
		}
	}
	
	
	public static boolean isRepeatSubmit(HttpSession httpSession,String token,String value) {
    	
        String serverToken = (String) httpSession.getAttribute(value);
        if (serverToken == null) {
            return true;
        }
        
        if (token == null) {
            return true;
        }
        
        if (!serverToken.equals(token)) {
            return true;
        }
        httpSession.removeAttribute("booktoken");
        return false;
    }
	
}
