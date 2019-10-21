package com.amlan.userauthenticator.pojo;

public class AddGroupPermissionRequest {

	private Integer groupId;
	
	private String permission;
	
	private Integer permissionGroupId;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getPermissionGroupId() {
		return permissionGroupId;
	}

	public void setPermissionGroupId(Integer permissionGroupId) {
		this.permissionGroupId = permissionGroupId;
	}
	
}
