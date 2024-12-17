package com.pratik.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pratik.dto.PasswordChngRequest;
import com.pratik.entity.User;
import com.pratik.repository.UserRepository;
import com.pratik.service.UserService;
import com.pratik.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepo;

	@Override
	public void changePassword(PasswordChngRequest passwordRequest) {

		User logedInUser = CommonUtil.getLoggedInUser();

		if (!passwordEncoder.matches(passwordRequest.getOldPassword(), logedInUser.getPassword())) {
			throw new IllegalArgumentException("Old Password is incorrect !!");
		}
		String encodePassword = passwordEncoder.encode(passwordRequest.getNewPassword());
		logedInUser.setPassword(encodePassword);
		userRepo.save(logedInUser);
	}

}