package com.amlan.userauthenticator.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application.properties")
public class MySQLProperties {

	@Value("${database}")
	private String url;
	
	@Value("${database}")
	private String driverClass;
	
	@Value("${database}")
	private String username;
	
	@Value("${database}")
	private String password;

	public String getUrl() {
		return url;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}
