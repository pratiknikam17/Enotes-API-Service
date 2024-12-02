package com.pratik.dto;

import java.time.LocalDateTime;
import java.util.Date;
 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


  
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotesDto {
	
	private Integer id;
	
	private String title;
	
	private String description;
	
	private CategoryDto category;
	
	private Integer createdby;
	
	private Date createdon;
	
	private Integer updatedby;
	
	private Date updatedon;
	
	private FilesDto fileDetails;
	
	private Boolean isDeleted;
	
	private LocalDateTime deletedOn;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FilesDto{
		private Integer id;
				
		private String originalFileName;
		
		private String displayFileName;
		
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CategoryDto{
		private Integer id;
		private String name;
	}
}
