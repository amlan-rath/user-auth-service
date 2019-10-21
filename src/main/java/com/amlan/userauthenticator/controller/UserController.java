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

import com.amlan.userauthenticator.config.CacheConfig;
import com.amlan.userauthenticator.dto.User;
import com.amlan.userauthenticator.pojo.LoginRequest;
import com.amlan.userauthenticator.pojo.PasswordChangeRequest;
import com.amlan.userauthenticator.pojo.TokenResponse;
import com.amlan.userauthenticator.service.UserService;

@RestController
@RequestMapping("/usermanagement-service/v1")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/user/login")
	public TokenResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
		TokenResponse token = userService.login(request);
		if (token.getToken() == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			CacheConfig.userTokenCache.put(request.getUserId(), token.getToken());
		}
		return token;
	}
	
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable("id") Integer id, HttpServletResponse response) {
		User user = userService.getUser(id);
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return user;
	}
	
	@GetMapping("/users")
	public List<User> getUsers(HttpServletRequest request, HttpServletResponse response) {
		List<User> userList =  userService.getUsers(request.getIntHeader("userId"), response);
		return userList;
	}
	
	@PostMapping("/user/create")
	public void insertUser(@RequestBody User user, HttpServletResponse response) {
		int status = userService.insertUser(user);
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
	
	@PostMapping("/user/changepassword/{id}")
	public void changePassword(@PathVariable("id") Integer id, @RequestBody PasswordChangeRequest request, HttpServletResponse response) {
		int status = userService.changePassword(id, request);
		if (status <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
