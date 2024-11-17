package com.pratik.entity;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass

public class BaseModel {
	
	private Boolean isactive;
	
	private Boolean isdeleted;
	
	private Integer createdby;
	
	private Date createdon;
	
	private Integer updatedby;
	
	private Date updatedon;
	
	
}
