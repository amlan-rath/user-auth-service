package com.amlan.userauthenticator;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import com.amlan.userauthenticator.config.CacheConfig;
import com.amlan.userauthenticator.constants.H2Queries;
import com.amlan.userauthenticator.enums.DatabaseEnum;
import com.amlan.userauthenticator.properties.GlobalProperties;

@Component
public class OnApplicationStartUp {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	DataSource dataSource;
	@Autowired
	GlobalProperties globalProperties;
	
	private static Logger logger = LoggerFactory.getLogger(OnApplicationStartUp.class);
	
	@EventListener
	public void onApplicationStartupEvent(ContextRefreshedEvent event) {
		logger.debug("In application startup event");
		initializeDependencies();
		DatabaseEnum dbEnum = DatabaseEnum.valueOf(globalProperties.getDatabase());
		String dbPathFolderName = dbEnum.getPathFolderName();
		if (noTablesExist(dbEnum)) {
			Resource schemaResource = new ClassPathResource("/database/" + dbPathFolderName + "/schema.sql");
			ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator(schemaResource);
			
			Resource dataResource = new ClassPathResource("/database/" + dbPathFolderName + "/data.sql");
			ResourceDatabasePopulator dataPopulator = new ResourceDatabasePopulator(dataResource);
			
			schemaPopulator.execute(dataSource);
			dataPopulator.execute(dataSource);
			logger.debug("Initialization data has been created");
		}
	}
	
	private void initializeDependencies() {
		CacheConfig.initialiseUserTokenCache();
	}
	
	private boolean noTablesExist(DatabaseEnum dbEnum) {
		try {
			Integer tableCount = 0;
			switch(dbEnum) {
				case H2:
					tableCount = jdbcTemplate.queryForObject(H2Queries.CHECK_NUMBER_OF_TABLES, Integer.class);
					logger.debug("Table Count found as - " + tableCount);
					break;
				case MYSQL:
					break;
				case POSTGRESQL:
					break;
			}		
			return tableCount == 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
