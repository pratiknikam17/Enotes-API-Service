package com.pratik.service;

import java.util.List;

import com.pratik.entity.Category;
 
public interface CategoryService {
	
	public Boolean saveCategory(Category category);
	
	public List<Category> getAllCategory(); 

}
