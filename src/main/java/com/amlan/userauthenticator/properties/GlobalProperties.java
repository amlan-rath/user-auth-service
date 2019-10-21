package com.amlan.userauthenticator.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application.properties")
public class GlobalProperties {

	@Value("${database}")
	private String database;
	
	public String getDatabase() {
		return database;
	}
}
