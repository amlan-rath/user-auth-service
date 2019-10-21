package com.amlan.userauthenticator.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.amlan.userauthenticator.properties.GlobalProperties;
import com.amlan.userauthenticator.properties.MySQLProperties;
import com.amlan.userauthenticator.enums.DatabaseEnum;

@Configuration
public class DatabaseConfig {
	
	@Autowired
	private GlobalProperties globalProperties;
	
	private static Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
	
	@Bean
	public DataSource dataSource() {
		logger.debug("In database config");
		String database = globalProperties.getDatabase();
		DatabaseEnum dbEnum = DatabaseEnum.valueOf(database);
		switch(dbEnum) {
			case H2: 
				return h2DataSource();
			case MYSQL:
				return mysqlDataSource();
			case POSTGRESQL:
				return postgresqlDataSource();
			default:
				return h2DataSource();
		}
	}
	
	private DataSource h2DataSource() {
		logger.debug("Data source going to be chosen as H2");
		return new EmbeddedDatabaseBuilder()
		        .setType(EmbeddedDatabaseType.H2)
		        .build();
	}
	
	private DataSource mysqlDataSource() {
		logger.debug("Data source going to be chosen as MYSQL");
		MySQLProperties mysqlProperties = new MySQLProperties();
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(mysqlProperties.getDriverClass());
        dataSource.setUrl(mysqlProperties.getUrl());
        dataSource.setUsername(mysqlProperties.getUsername());
        dataSource.setPassword(mysqlProperties.getPassword());
        return dataSource;
	}
	
	private DataSource postgresqlDataSource() {
		return null;
	}
	
}
