package com.amlan.userauthenticator.enums;

public enum DatabaseEnum {
	H2("h2"), MYSQL("mysql"), POSTGRESQL("postgresql");
	
	String pathFolderName;
	
	private DatabaseEnum(String name) {
		this.pathFolderName = name;
	}

	public String getPathFolderName() {
		return pathFolderName;
	}
}
