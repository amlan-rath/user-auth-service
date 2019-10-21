package com.amlan.userauthenticator.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amlan.userauthenticator.dto.Group;
import com.amlan.userauthenticator.dto.User;
import com.amlan.userauthenticator.pojo.AddGroupPermissionRequest;
import com.amlan.userauthenticator.pojo.AddUserToGroupRequest;
import com.amlan.userauthenticator.service.GroupService;

@RestController
@RequestMapping("/usermanagement-service/v1")
public class GroupController {

	@Autowired
	GroupService groupService;
	
	@GetMapping("/group/{id}")
	public List<User> getUsersInGroup(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
		return groupService.getUsersInGroup(id, request.getIntHeader("userId"), response);
	}
	
	@PostMapping("/group/create")
	public void createGroup(@RequestBody Group group, HttpServletRequest request, HttpServletResponse response) {
		int status = groupService.createGroup(group, request.getIntHeader("userId"), response);
		switch(status) {
			case 1:
				response.setStatus(HttpServletResponse.SC_CREATED);
				break;
			case 0:
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				break;
			case -1:
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@PostMapping("/group/adduser")
	public void addUserToGroup(@RequestBody AddUserToGroupRequest requestBody, HttpServletRequest request, HttpServletResponse response) {
		int status = groupService.addUserToGroup(requestBody, request.getIntHeader("userid"), response);
		switch(status) {
			case 1:
				response.setStatus(HttpServletResponse.SC_CREATED);
				break;
			case 0:
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				break;
			case -1:
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@PostMapping("group/addpermission")
	public void addPermissionToGroup(@RequestBody AddGroupPermissionRequest requestBody, HttpServletRequest request, HttpServletResponse response) {
		int status = groupService.addPermissionToGroup(requestBody, request.getIntHeader("userId"), response);
		switch(status) {
			case 1:
				response.setStatus(HttpServletResponse.SC_CREATED);
				break;
			case 0:
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				break;
			case -1:
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
}
