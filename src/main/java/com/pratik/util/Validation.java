package com.pratik.util;

import java.security.PrivateKey;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.pratik.dto.CategoryDto;
import com.pratik.dto.TodoDto;
import com.pratik.dto.TodoDto.StatusDto;
import com.pratik.dto.UserDto;
import com.pratik.entity.Role;
import com.pratik.enums.TodoStatus;
import com.pratik.exception.ResourceNotFoundException;
import com.pratik.exception.ValidationException;
import com.pratik.repository.RoleRepository;

@Component
public class Validation {
	
	@Autowired
	private RoleRepository roleRepo;
	 
	public void categoryValidation(CategoryDto categoryDto) {
		
		Map<String, Object> error = new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category object/JSON shouldn't be null or empty");
			
		} else {
			// validation name field
			if(ObjectUtils.isEmpty(categoryDto.getName())){
				throw new IllegalArgumentException("name field is empty or null");
			}
			else {
				if(categoryDto.getName().length() < 3) {
					error.put("name", "name length min 3");
				}
				if(categoryDto.getName().length() > 100) {
					error.put("name", "name length max 100");
				}
			}
			
			//validation description 
			if(ObjectUtils.isEmpty(categoryDto.getDescription())){
				error.put("description", "description field is empty or null");
			}		
			
			//validation isActive
			if(ObjectUtils.isEmpty(categoryDto.getIsactive())){
				error.put("isActive", "isActive field is empty or null");
			}
			else {
				if(categoryDto.getIsactive()  != Boolean.TRUE.booleanValue() && categoryDto.getIsactive() != Boolean.FALSE.booleanValue() ) {
					error.put("isActive", "invalid value isActive field ");
				}
				
			}
			
		}
		if(!error.isEmpty()) {
			throw new ValidationException(error);
			
		}
		
	}
	
	public void todoValidation(TodoDto todo) throws Exception {
		StatusDto reqStatus = todo.getStatus();
		Boolean statusFound = false;
		
		for(TodoStatus st:TodoStatus.values()) {
			if(st.getId().equals(reqStatus.getId())) {
				statusFound=true;
			}
		}
		
		if(!statusFound) {
			throw new ResourceNotFoundException("Invalid status");
		}
	}
	
	public void userValidation(UserDto userDto) {
		
		
		if(!StringUtils.hasText(userDto.getFirstName())) {
			throw new IllegalArgumentException("First name is invalid");	
		}
		if(!StringUtils.hasText(userDto.getLastName())) {
			throw new IllegalArgumentException("Last name is invalid");	
		}
		
		if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX) ) {
			throw new IllegalArgumentException("Email is invalid");	
		}
		
		if(!StringUtils.hasText(userDto.getMobno()) || !userDto.getMobno().matches(Constants.MOBNO_REGEX)) {
			throw new IllegalArgumentException("Mobile Number is invalid");	
		} 
		
		if(CollectionUtils.isEmpty(userDto.getRoles())) {
			throw new IllegalArgumentException("Role is invalid");	
			
		} else {
			List<Integer> roleIds = roleRepo.findAll().stream().map(r->r.getId()).toList();
			
			List<Integer> invalidReqRoleIds= userDto.getRoles().stream().map(r -> r.getId()).filter(roleId -> !roleIds.contains(roleId)).toList();
			
			if(!CollectionUtils.isEmpty(invalidReqRoleIds)) {
				throw new IllegalArgumentException("Role is invalid"+invalidReqRoleIds);	
			}
		
		}
	}
	
	
	
}
 