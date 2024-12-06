package com.pratik.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pratik.dto.UserDto;
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
		
	@Override
	public Boolean register(UserDto userDto) {
		
		validation.userValidation(userDto);
		User user= mapper.map(userDto, User.class);
		setRole(userDto,user);
		
		User saveUser= userRepo.save(user);
		if(!ObjectUtils.isEmpty(saveUser)) {
			return true;
		}
		 	
		return false;
	}

	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
	}

	
}
