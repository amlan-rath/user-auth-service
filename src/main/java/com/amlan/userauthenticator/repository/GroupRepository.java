package com.amlan.userauthenticator.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amlan.userauthenticator.dto.Group;
import com.amlan.userauthenticator.dto.User;
import com.amlan.userauthenticator.enums.PermissionTypeEnum;
import com.amlan.userauthenticator.pojo.AddGroupPermissionRequest;
import com.amlan.userauthenticator.pojo.AddUserToGroupRequest;

@Repository
public class GroupRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String GET_ALL_USERS_IN_GROUP = "select u.id, u.user_name, u.first_name, u.last_name"
			+ " from user.users u"
			+ " join user.user_group_mapping ugm on u.id = ugm.user_id"
			+ " where ugm.group_id = ?";
	private static final String INSERT_NEW_GROUP = "insert into user.groups (group_name) values (?)";
	private static final String ADD_USER_TO_GROUP = "insert into user.user_group_mapping (group_id, user_id) values (?, ?)";
	private static final String ADD_PERMISSION_TO_GROUP = "insert into user.group_permission_mapping (parent_group_id, permission, permission_group_id)"
			+ " values (?, ?, ?)";
	
	public List<User> getUsersInGroup(Integer groupId) {
		try {
			return jdbcTemplate.query(GET_ALL_USERS_IN_GROUP, new Object[] {groupId}, new User.UserRowMapper());
		} catch(Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		
	}
	
	public int createGroup(Group group) {
		try {
			return jdbcTemplate.update(INSERT_NEW_GROUP, group.getGroupName());
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int addUserToGroup(AddUserToGroupRequest request) {
		try {
			return jdbcTemplate.update(ADD_USER_TO_GROUP, request.getGroupId(), request.getUserId());
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int addPermissionToGroup(AddGroupPermissionRequest request) {
		try {
			PermissionTypeEnum pEnum = PermissionTypeEnum.valueOf(request.getPermission());
			return jdbcTemplate.update(ADD_PERMISSION_TO_GROUP, request.getGroupId(), pEnum.name(), request.getPermissionGroupId());
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
}
