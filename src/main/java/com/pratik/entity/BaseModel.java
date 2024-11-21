package com.pratik.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass

public abstract class BaseModel {
	
	@CreatedBy
	@Column(updatable = false)
	private Integer createdby;
	
	@CreatedDate
	@Column(updatable = false)
	private Date createdon;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer updatedby;
	
	@LastModifiedDate
	@Column(insertable = false)
	private Date updatedon;
	
	
}
