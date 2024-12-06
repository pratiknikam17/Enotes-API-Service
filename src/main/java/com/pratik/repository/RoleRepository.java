package com.pratik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratik.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
