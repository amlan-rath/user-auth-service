package com.amlan.userauthenticator.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amlan.userauthenticator.dto.User;
import com.amlan.userauthenticator.enums.PermissionTypeEnum;
import com.amlan.userauthenticator.pojo.LoginRequest;
import com.amlan.userauthenticator.pojo.PasswordChangeRequest;
import com.amlan.userauthenticator.pojo.TokenResponse;
import com.amlan.userauthenticator.repository.PermissionRepository;
import com.amlan.userauthenticator.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PermissionRepository permissionRepository;
	
	public TokenResponse login(LoginRequest request) {
		if (userRepository.validateUser(request)) {
			return new TokenResponse(UUID.randomUUID().toString());
		}
		return new TokenResponse(null);
	}
	
	public User getUser(Integer id) {
		return userRepository.getUser(id);
	}
	
	public List<User> getUsers(Integer userId, HttpServletResponse response) {
		boolean authorized = permissionRepository.checkForPermission(userId, PermissionTypeEnum.VIEW_ALL, -1);
		if (!authorized) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return Collections.emptyList();
		}
		return userRepository.getUsers();
	}
	
	public int insertUser(User user) {
		return userRepository.insertUser(user);
	}
	
	public int changePassword(int userId, PasswordChangeRequest request) {
		return userRepository.changePassword(userId, request);
	}
}
