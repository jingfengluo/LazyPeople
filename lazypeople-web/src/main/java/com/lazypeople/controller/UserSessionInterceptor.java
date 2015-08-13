package com.lazypeople.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class UserSessionInterceptor implements HandlerInterceptor {
	
	static BaseController baseCtrl  = null;
	
	static{
		if(baseCtrl == null){
			baseCtrl = new BaseController();
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		 // 不过滤的uri  
		String[] notFilter = new String[] {"login", "logout", "notifyRechargeUrl"};  
		String uri = request.getRequestURI();
		boolean doFilter = true;
		
		// 是否过滤  
        for (String s : notFilter) {  
            if (uri.indexOf(s) != -1) {  
            	doFilter = false;
                break;  
            }  
        }  
        
        if(doFilter){
        	if(!baseCtrl.checkUserSession(request.getSession())){
        		return false;
        	}
        }
		
        return true;
	}

}
