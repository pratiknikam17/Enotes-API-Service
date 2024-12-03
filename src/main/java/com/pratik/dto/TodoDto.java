package com.pratik.dto;

import java.util.Date;

import com.pratik.entity.Todo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TodoDto {
	
	private Integer id;
	
	private String title;
	
	private StatusDto status;
	
	private Integer createdby;
	
	private Date createdon;
	
	private Integer updatedby;
	
	private Date updatedon;
	
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor 
	@Getter
	@Setter


	public static class StatusDto{
		private Integer id;
		private String name;
	}
	
}
