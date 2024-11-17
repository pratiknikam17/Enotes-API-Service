package com.pratik.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pratik.dto.CategoryDto;
import com.pratik.dto.CategoryResponse;
import com.pratik.entity.Category;
import com.pratik.repository.CategoryRepository;
import com.pratik.service.CategoryService;

@Service

public class CategoryServiceImpl implements CategoryService  {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	 
	@Override
	public Boolean saveCategory(CategoryDto categorydto) {
		
//		Category category=new Category();
//		category.setName(categorydto.getName());
//		category.setDescription(categorydto.getDescription());
//		category.setIs_active(categorydto.getIs_active());
		
		Category category=mapper.map(categorydto,Category.class);
		category.setIsdeleted(false);
		category.setCreatedby(1);
		category.setCreatedon(new Date());
		Category saveCategory = categoryRepo.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true; 
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepo.findAll();
		  
		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = categoryRepo.findByIsactiveTrue();
		
		List<CategoryResponse> categoryList = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();
		
		return categoryList;
	}

}