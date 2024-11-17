package com.pratik.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pratik.entity.Category;
import com.pratik.repository.CategoryRepository;
import com.pratik.service.CategoryService;

@Service

public class CategoryServiceImpl implements CategoryService  {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Boolean saveCategory(Category category) {
		category.setIs_deleted(false);
		category.setCreated_by(1);
		category.setCreated_on(new Date());
		Category saveCategory = categoryRepo.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;   
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> categories = categoryRepo.findAll();
		return categories;
	}

}
