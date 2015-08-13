package com.lazypeople.filter;

import java.io.IOException;  
  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
public class NoCacheFilter implements Filter {  
  
    @Override  
    public void destroy() {  
        // TODO Auto-generated method stub  
  
    }  
  
    @Override  
    public void doFilter(ServletRequest req, ServletResponse res,  
            FilterChain chain) throws IOException, ServletException {  
  
        HttpServletResponse response = (HttpServletResponse) res;  
        HttpServletRequest request = (HttpServletRequest) req;  
  
        response.setDateHeader("Expires", -1);  
        response.setHeader("Cache-Control", "no-cache");  
        response.setHeader("Pragma", "no-cache");  
        // 修改  
        chain.doFilter(request, response);// 执行目标方法  
    }  
  
    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {  
        // TODO Auto-generated method stub  
  
    }  
  
}  