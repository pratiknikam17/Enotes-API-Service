package com.pratik.entity;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass

public class BaseModel {
	
	private Boolean is_active;
	
	private Boolean is_deleted;
	
	private Integer created_by;
	
	private Date created_on;
	
	private Integer updated_by;
	
	private Date updated_on;

	
	
}
