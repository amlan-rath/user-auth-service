package com.amlan.userauthenticator.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.amlan.userauthenticator.config.CacheConfig;

public class AuthorizationInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
	
	private static final Set<String> ALLOWED_URI = new HashSet<>(Arrays.asList("/user/login", "/user/create"));
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    String requestUri = request.getRequestURI();
	    logger.debug("Got URI - " + requestUri);
	    if (checkIfAuthenticationIsRequired(requestUri)) {
	    	Integer userId = request.getIntHeader("userId");
	    	String token = request.getHeader("token");
	    	String tokenInCache = CacheConfig.userTokenCache.getIfPresent(userId);
	    	if (tokenInCache != null && tokenInCache.equals(token)) {
	    		return true;
	    	} else {
	    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    		return false;
	    	}
	    }
	    return true;
	}
	
	private boolean checkIfAuthenticationIsRequired(String uri) {
		for (String s : ALLOWED_URI) {
			if (uri.contains(s)) {
				return false;
			}
		}
		return true;
	}
	
}
