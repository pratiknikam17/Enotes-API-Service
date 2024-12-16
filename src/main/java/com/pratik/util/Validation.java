package com.pratik.util;

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
import com.pratik.dto.UserRequest;
import com.pratik.enums.TodoStatus;
import com.pratik.exception.ExistDataException;
import com.pratik.exception.ResourceNotFoundException;
import com.pratik.exception.ValidationException;
import com.pratik.repository.RoleRepository;
import com.pratik.repository.UserRepository;

@Component
public class Validation {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserRepository userRepo;

	public void categoryValidation(CategoryDto categoryDto) {

		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category Object/JSON shouldn't be null or empty");
		} else {

			// validation name field
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is empty or null");
			} else {
				if (categoryDto.getName().length() < 3) {
					error.put("name", "name length min 3");
				}
				if (categoryDto.getName().length() > 100) {
					error.put("name", "name length max 100");
				}
			}

			// validation dscription
			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description field is empty or null");
			}

			// validation isActive
			if (ObjectUtils.isEmpty(categoryDto.getIsactive())) {
				error.put("isActive", "isActive field is empty or null");
			} else {
				if (categoryDto.getIsactive() != Boolean.TRUE.booleanValue()
						&& categoryDto.getIsactive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive", "invalid value isActive field ");
				}
			}
		}
		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}

	}

	public void todoValidation(TodoDto todo) throws Exception {
		StatusDto reqStatus = todo.getStatus();
		Boolean statusFound = false;
		for (TodoStatus st : TodoStatus.values()) {
			if (st.getId().equals(reqStatus.getId())) {
				statusFound = true;
			}
		}
		if (!statusFound) {
			throw new ResourceNotFoundException("invalid status");
		}
	}

	public void userValidation(UserRequest userDto) {

		if (!StringUtils.hasText(userDto.getFirstName())) {
			throw new IllegalArgumentException("first name is invalid");
		}

		if (!StringUtils.hasText(userDto.getLastName())) {
			throw new IllegalArgumentException("last name is invalid");
		}

		if (!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)) {
			throw new IllegalArgumentException("email is invalid");
		} else {
			// validate email exist
			Boolean existEmail = userRepo.existsByEmail(userDto.getEmail());
			if (existEmail) {
				throw new ExistDataException("Email already exist");
			}
		}

		if (!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.MOBNO_REGEX)) {
			throw new IllegalArgumentException("mobno is invalid");
		}

		if (CollectionUtils.isEmpty(userDto.getRoles())) {
			throw new IllegalArgumentException("role is invalid");
		} else {

			List<Integer> roleIds = roleRepo.findAll().stream().map(r -> r.getId()).toList();

			List<Integer> invalidReqRoleids = userDto.getRoles().stream().map(r -> r.getId())
					.filter(roleId -> !roleIds.contains(roleId)).toList();

			if (!CollectionUtils.isEmpty(invalidReqRoleids)) {
				throw new IllegalArgumentException("role is invalid" + invalidReqRoleids);
			}
		}

	}

}