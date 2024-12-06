package com.pratik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratik.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Boolean existsByEmail(String email);
	
}
