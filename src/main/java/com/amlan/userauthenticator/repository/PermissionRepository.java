package com.amlan.userauthenticator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amlan.userauthenticator.enums.PermissionTypeEnum;

@Repository
public class PermissionRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String CHECK_IF_PERMISSION_EXISTS = "select count(*) from"
			+ " user.users u"
			+ " join user.user_group_mapping ugm on u.id = ugm.user_id"
			+ " join user.group_permission_mapping gpm on ugm.group_id = gpm.parent_group_id"
			+ " where u.id = ? and ((gpm.permission = ? and gpm.permission_group_id = -1)"
			+ " or (gpm.permission = ? and gpm.permission_group_id = ?))";
	
	public boolean checkForPermission(Integer userId, PermissionTypeEnum permission, Integer permissionGroupId) {
		try {
			PermissionTypeEnum allTypePermission = getAllTypePermission(permission);
			return jdbcTemplate.queryForObject(CHECK_IF_PERMISSION_EXISTS, new Object[] {userId, allTypePermission.name(),
					permission.name(), permissionGroupId}, Integer.class) > 0;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public PermissionTypeEnum  getAllTypePermission(PermissionTypeEnum permissionEnum) {
		switch(permissionEnum) {
			case VIEW_ALL:
				return PermissionTypeEnum.VIEW_ALL;
			case VIEW_ANY_GROUP:
			case VIEW_GROUP:
				return PermissionTypeEnum.VIEW_ANY_GROUP;
			case CREATE_GROUP:
				return PermissionTypeEnum.CREATE_GROUP;
			case MAP_ANY_USER_TO_ANY_GROUP:
			case MAP_USER_TO_GROUP:
				return PermissionTypeEnum.MAP_ANY_USER_TO_ANY_GROUP;
			case ASSIGN_PERMISSION_TO_ALL:
			case ASSIGN_PERMISSION_TO_GROUP:
				return PermissionTypeEnum.ASSIGN_PERMISSION_TO_ALL;
			default:
				return null;
		}
	}
}
