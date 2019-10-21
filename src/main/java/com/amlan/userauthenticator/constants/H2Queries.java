package com.amlan.userauthenticator.constants;

public class H2Queries {

	public static final String CHECK_NUMBER_OF_TABLES = "select count(*) from information_schema.tables where table_schema != 'INFORMATION_SCHEMA'";
	
}
