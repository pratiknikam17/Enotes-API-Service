package com.pratik.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto {

	private Integer id;

	private String name;
	
	private String description;
	
	private Boolean isactive;
		
	private Integer createdby;
	
	private Date createdon;
	
	private Integer updatedby;
	
	private Date updatedon;
}
