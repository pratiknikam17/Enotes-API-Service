package com.pratik.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter 
@EntityListeners(AuditingEntityListener.class)

public class Category extends BaseModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;

	private String name;
	
	private String description;
	
	private Boolean isactive;
	
	private Boolean isdeleted;

	

}


