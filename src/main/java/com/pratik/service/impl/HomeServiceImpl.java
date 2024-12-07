package com.pratik.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pratik.entity.AccountStatus;
import com.pratik.entity.User;
import com.pratik.exception.ResourceNotFoundException;
import com.pratik.exception.SuccessException;
import com.pratik.repository.UserRepository;
import com.pratik.service.HomeService;

@Component
public class HomeServiceImpl  implements HomeService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		
		User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid User"));
		
		if(user.getStatus().getVerificationCode() == null) {
			throw new SuccessException("Account already verified");
		}
		
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status=user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			
			userRepo.save(user);
			return true;
		}
		
		return false;
	}

}
