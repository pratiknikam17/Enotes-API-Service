package com.pratik.service;

import java.util.List;

import com.pratik.dto.TodoDto;

public interface TodoService {
	
	public Boolean saveTodo(TodoDto todo) throws Exception;
	
	public TodoDto getTodoById(Integer id) throws Exception;
	
	public List<TodoDto> getTodoByuser();

	
	
}
