package com.pratik.service;

import com.pratik.dto.LoginRequest;
import com.pratik.dto.LoginResponse;
import com.pratik.dto.UserDto;

public interface UserService {

	public Boolean register(UserDto userDto, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);
	
}
