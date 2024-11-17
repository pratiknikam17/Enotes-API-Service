package com.pratik.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratik.dto.CategoryDto;
import com.pratik.dto.CategoryResponse;
import com.pratik.entity.Category;
import com.pratik.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categorydto){
		Boolean saveCategory = categoryService.saveCategory(categorydto);
		
		if(saveCategory) {
			return new ResponseEntity<>("saved success", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory(){
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();	 
		}
		else {
			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
	}
	
	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategory(){
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();	
		}
		else {
			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
	}
}
