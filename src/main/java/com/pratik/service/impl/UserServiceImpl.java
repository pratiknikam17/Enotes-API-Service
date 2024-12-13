package com.pratik.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pratik.config.security.CustomUserDetails;
import com.pratik.dto.EmailRequest;
import com.pratik.dto.LoginRequest;
import com.pratik.dto.LoginResponse;
import com.pratik.dto.UserDto;
import com.pratik.entity.AccountStatus;
import com.pratik.entity.Role;
import com.pratik.entity.User;
import com.pratik.repository.RoleRepository;
import com.pratik.repository.UserRepository;
import com.pratik.service.UserService;
import com.pratik.util.Validation;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
		
	@Override
	public Boolean register(UserDto userDto,String url) throws Exception {
		
		validation.userValidation(userDto);
		User user= mapper.map(userDto, User.class);
		setRole(userDto,user);
		
		AccountStatus status=AccountStatus.builder()
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString())
				.build();
		user.setStatus(status);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User saveUser= userRepo.save(user);
		if(!ObjectUtils.isEmpty(saveUser)) {
			
			//Send  Email
			emailSend(saveUser,url);
			return true;
		}
		 	
		return false;
	}

	private void emailSend(User saveUser, String url) throws Exception {
		
		String message = "Hi, <b>[[username]]</b>"
		+"<br>Your account registration successfully.<br>"
		+"<br>Click the below link to verify & active your account<br>"
		+"<a href='[[url]]'>Click Here</a> <br><br>"
		+"Thanks,<br> <b>Enotes.com</b>";
		
		message = message.replace("[[username]]", saveUser.getFirstName());
		message=message.replace("[[url]]",url+"/api/v1/home/verify?uid="+saveUser.getId()+"&&code="+saveUser.getStatus().getVerificationCode());
		EmailRequest emailRequest=EmailRequest.builder()
				.to(saveUser.getEmail())
				.title("Account Creation Confirmation")
				.subject("Account Created Success")
				.message(message)
				.build();
		
		emailService.sendEmail(emailRequest);
		
	}

	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			 CustomUserDetails customUserDetails=(CustomUserDetails)authenticate.getPrincipal();
			
			String token="sdfdfsfdfdfsdfdfdfdfdfdfdfdfdfd";
			LoginResponse loginResponse=LoginResponse.builder()
	 				.user(mapper.map(customUserDetails.getUser(), UserDto.class))
					.token(token)
					.build(); 
			
			return loginResponse;
		}
		return null;
	}

	
	
}