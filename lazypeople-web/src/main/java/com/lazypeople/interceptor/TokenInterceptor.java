package com.lazypeople.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>
 * 防止重复提交过滤器
 * </p>
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
	 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html; charset=utf-8");
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                	String tokenstr = UUID.randomUUID().toString();
                    request.getSession().setAttribute(annotation.value(), tokenstr);
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                	return true;
                }
            }
            
        } else {
            return super.preHandle(request, response, handler);
        }
		return true;
    }
 
}