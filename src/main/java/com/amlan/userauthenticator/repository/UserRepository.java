package com.amlan.userauthenticator.repository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amlan.userauthenticator.dto.User;
import com.amlan.userauthenticator.pojo.LoginRequest;
import com.amlan.userauthenticator.pojo.PasswordChangeRequest;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String GET_USER_INFO = "select id, user_name, first_name, last_name from user.users where id = ?";
	private static final String GET_ALL_USER_INFO = "select id, user_name, first_name, last_name from user.users";
	private static final String INSERT_USER_INFO = "insert into user.users (user_name, first_name, last_name, password) values (?, ?, ?, ?)";
	private static final String GET_USER_CURRENT_PASSWORD = "select password from user.users where id = ?";
	private static final String UPDATE_USER_PASSWORD = "update user.users set password = ? where id = ?";
	
	Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	public boolean validateUser(LoginRequest request) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String userEnteredPasswordHash = Base64.getEncoder().encodeToString(digest.digest(request.getPassword()
													.getBytes(StandardCharsets.UTF_8)));
			String passwordSaved =  jdbcTemplate.queryForObject(GET_USER_CURRENT_PASSWORD, new Object[] {request.getUserId()}, String.class);
			return passwordSaved.equals(userEnteredPasswordHash);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User getUser(Integer id) {
		try {
			return jdbcTemplate.queryForObject(GET_USER_INFO, new Object[] {id}, new User.UserRowMapper());
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getUsers() {
		try {
			return jdbcTemplate.query(GET_ALL_USER_INFO, new User.UserRowMapper());
		} catch(Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public int insertUser(User user) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String passwordHash = Base64.getEncoder().encodeToString(digest.digest(user.getPassword().getBytes(StandardCharsets.UTF_8)));
			int rowsInserted = jdbcTemplate.update(INSERT_USER_INFO, user.getUserName(), user.getFirstName(), user.getLastName(), passwordHash);
			return rowsInserted;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int changePassword(int userId, PasswordChangeRequest request) {
		try {
			String userCurrentPassword = jdbcTemplate.queryForObject(GET_USER_CURRENT_PASSWORD, new Object[] {userId}, String.class);
			if (userCurrentPassword == null) {
				return 0;
			}
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String userEnteredCurrentPasswordHash = Base64.getEncoder().encodeToString(digest.digest(request.getOldPassword()
													.getBytes(StandardCharsets.UTF_8)));
			if (!userCurrentPassword.equals(userEnteredCurrentPasswordHash)) {
				return 0;
			}
			String userEnteredNewPasswordHash = Base64.getEncoder().encodeToString(digest.digest(request.getNewPassword()
												.getBytes(StandardCharsets.UTF_8)));
			int rowsUpdated = jdbcTemplate.update(UPDATE_USER_PASSWORD, userEnteredNewPasswordHash, userId);
			return rowsUpdated;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
