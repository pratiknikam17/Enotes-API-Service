package com.pratik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratik.dto.UserDto;
import com.pratik.service.UserService;
import com.pratik.util.CommonUtil;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<?> registeruser(@RequestBody UserDto userDto,HttpServletRequest request) throws Exception{
		String url=CommonUtil.getUrl(request);
		
		Boolean register = userService.register(userDto,url);
		if(register) {
			return CommonUtil.createBuildResponseMessage("Register success", HttpStatus.CREATED);
		} 
		return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);
 
	}
} 
