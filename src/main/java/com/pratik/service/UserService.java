package com.pratik.service;

import com.pratik.dto.LoginRequest;
import com.pratik.dto.LoginResponse;
import com.pratik.dto.UserRequest;

public interface UserService {

	public Boolean register(UserRequest userDto, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);
	
}
