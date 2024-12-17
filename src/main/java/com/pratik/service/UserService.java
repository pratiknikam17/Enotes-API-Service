package com.pratik.service;


import com.pratik.dto.PasswordChngRequest;

public interface UserService {

	public void changePassword(PasswordChngRequest passwordRequest);
}
