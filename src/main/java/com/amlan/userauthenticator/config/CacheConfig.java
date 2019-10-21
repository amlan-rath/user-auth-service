package com.amlan.userauthenticator.config;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheConfig {
	
	private static Logger logger = LoggerFactory.getLogger(CacheConfig.class);
	
	public static Cache<Integer, String> userTokenCache;
	
	public static void initialiseUserTokenCache() {
		logger.debug("Initialising Guava Cache");
		userTokenCache = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).build();
		
	}
	
}
