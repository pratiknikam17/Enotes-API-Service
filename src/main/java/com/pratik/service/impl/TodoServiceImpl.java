package com.pratik.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pratik.dto.TodoDto;
import com.pratik.dto.TodoDto.StatusDto;
import com.pratik.entity.Todo;
import com.pratik.enums.TodoStatus;
import com.pratik.exception.ResourceNotFoundException;
import com.pratik.repository.TodoRepository;
import com.pratik.service.TodoService;
import com.pratik.util.Validation;

@Service
public class TodoServiceImpl implements TodoService{

	
	@Autowired
	private TodoRepository todoRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	
	@Override
	public Boolean saveTodo(TodoDto todoDto) throws Exception {
		// validate todo status
		validation.todoValidation(todoDto);
		
		Todo todo = mapper.map(todoDto, Todo.class);
		todo.setStatusId(todoDto.getStatus().getId());
		Todo saveTodo = todoRepo.save(todo);
		if(!ObjectUtils.isEmpty(saveTodo)) {
			return true;
		}
		return false;
	}

	@Override
	public TodoDto getTodoById(Integer id) throws Exception {
		Todo todo=todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo Not found ! id invalid"));
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		setStatus(todoDto,todo);
		return todoDto;
	}

	private void setStatus(TodoDto todoDto, Todo todo) {
		for( TodoStatus st:TodoStatus.values()) {
			if(st.getId().equals(todo.getStatusId())) {
				StatusDto statusDto=StatusDto.builder()
						.id(st.getId())
						.name(st.getName())
						.build();
				todoDto.setStatus(statusDto);
			}
		}
		
	}

	@Override
	public List<TodoDto> getTodoByuser() {
		Integer userId=2;
		
		List<Todo> todos=todoRepo.findByCreatedby(userId);
		
		return todos.stream().map(td -> mapper.map(td, TodoDto.class)).toList();
	}
	

}
