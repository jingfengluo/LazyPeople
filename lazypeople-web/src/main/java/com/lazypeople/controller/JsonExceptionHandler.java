package com.lazypeople.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lazypeople.logger.Logger;
import com.lazypeople.web.exception.ValidationException;

/**
 * Called when an exception occurs during request processing. Transforms the
 * exception message into JSON format.
 */
@Component
public class JsonExceptionHandler implements HandlerExceptionResolver {

	@Logger
	Log log;
	private ObjectMapper mapper = new ObjectMapper();

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		try {
			Map<String, Object> error = new HashMap<String, Object>();
			error.put("type", "danger");
			error.put("text", ex.getMessage());
			if (ex instanceof ValidationException) {
				error.put("code", ((ValidationException) ex).getCode());
				error.put("show", false);
				log.error("Uncatched Error occued! ValidationException Message: "+ex.getMessage());
			} else {
				int tmpCode = new Random().nextInt(1000000);
				error.put("show", true);		
				error.put("text", "系统服务发生异常，错误码：["+tmpCode+"]，请联系管理员！");
				log.error("Uncatched Error occued! code:"+tmpCode, ex);
			}
			response.setStatus(400);
			mapper.writeValue(response.getWriter(), error);
		} catch (IOException e) {
			log.error("resolveException error:", e);
		}
		return new ModelAndView();
	}
}
