package com.pratik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratik.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
	List<Todo> findByCreatedby(Integer userId);
}
