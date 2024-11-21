package com.pratik.controller;

import java.lang.System.Logger;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratik.dto.CategoryDto;
import com.pratik.dto.CategoryResponse;
import com.pratik.entity.Category;
import com.pratik.exception.ResourceNotFoundException;
import com.pratik.service.CategoryService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categorydto){
		Boolean saveCategory = categoryService.saveCategory(categorydto);
		
		if(saveCategory) {
			return new ResponseEntity<>("saved success", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory(){ 
//		String nm=null;
//		nm.toUpperCase();
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();	 
		}
		else {
			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
	}
	 
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategory(){
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();	
		}
		else {
			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{
		
//		try {
//			CategoryDto categoryDto = categoryService.getCategoryById(id);
//			if(ObjectUtils.isEmpty(categoryDto)) {
//				return new ResponseEntity<>("Category not found with Id=" + id, HttpStatus.NOT_FOUND);
//			}
//			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
//			
//		} 
//		catch (ResourceNotFoundException e) {
//			log.error("Controller :: getCategoryDetailsById ::",e.getMessage());
//			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//		}
//		catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//		}
		
		CategoryDto categoryDto = categoryService.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryDto)) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
		Boolean deleted = categoryService.deleteCategory(id);
		
		if(deleted) {
			return new ResponseEntity<>("Category deleted success", HttpStatus.OK);
		}
		return new ResponseEntity<>("Category Not Deleted",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
