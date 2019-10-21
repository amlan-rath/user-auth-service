package com.amlan.userauthenticator.service;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amlan.userauthenticator.dto.Group;
import com.amlan.userauthenticator.dto.User;
import com.amlan.userauthenticator.enums.PermissionTypeEnum;
import com.amlan.userauthenticator.pojo.AddGroupPermissionRequest;
import com.amlan.userauthenticator.pojo.AddUserToGroupRequest;
import com.amlan.userauthenticator.repository.GroupRepository;
import com.amlan.userauthenticator.repository.PermissionRepository;

@Service
public class GroupService {

	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	PermissionRepository permissionRepository;
	
	public List<User> getUsersInGroup(Integer groupId, Integer userId, HttpServletResponse response) {
		boolean authorized = permissionRepository.checkForPermission(userId, PermissionTypeEnum.VIEW_GROUP, groupId);
		if (!authorized) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return Collections.emptyList();
		}
		return groupRepository.getUsersInGroup(groupId);
	}
	
	public int createGroup(Group group, Integer userId, HttpServletResponse response) {
		boolean authorized = permissionRepository.checkForPermission(userId, PermissionTypeEnum.CREATE_GROUP, -1);
		if (!authorized) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return -2;
		}
		return groupRepository.createGroup(group);
	}
	
	public int addUserToGroup(AddUserToGroupRequest request, Integer userId, HttpServletResponse response) {
		boolean authorized = permissionRepository.checkForPermission(userId, PermissionTypeEnum.MAP_USER_TO_GROUP, request.getGroupId());
		if (!authorized) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return -2;
		}
		return groupRepository.addUserToGroup(request);
	}
	
	public int addPermissionToGroup(AddGroupPermissionRequest request, Integer userId, HttpServletResponse response) {
		boolean authorized = permissionRepository.checkForPermission(userId, PermissionTypeEnum.ASSIGN_PERMISSION_TO_GROUP, request.getGroupId());
		if (!authorized) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return -2;
		}
		return groupRepository.addPermissionToGroup(request);
	}
	
}
