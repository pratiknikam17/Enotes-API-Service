package com.pratik.service;

import java.util.List;

import com.pratik.dto.CategoryDto;
import com.pratik.dto.CategoryResponse;
import com.pratik.entity.Category;
 
public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categorydto);
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory(); 

}
