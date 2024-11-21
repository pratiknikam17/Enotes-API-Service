package com.pratik.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.pratik.dto.CategoryDto;
import com.pratik.exception.ValidationException;

@Component
public class Validation {
	
	public void categoryValidation(CategoryDto categoryDto) {
		
		Map<String, Object> error = new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category object/JSON shouldn't be null or empty");
			
		} else {
			// validation name field
			if(ObjectUtils.isEmpty(categoryDto.getName())){
				throw new IllegalArgumentException("name field is empty or null");
			}
			else {
				if(categoryDto.getName().length() < 3) {
					error.put("name", "name length min 3");
				}
				if(categoryDto.getName().length() > 100) {
					error.put("name", "name length max 100");
				}
			}
			
			//validation description 
			if(ObjectUtils.isEmpty(categoryDto.getDescription())){
				error.put("description", "description field is empty or null");
			}		
			
			//validation isActive
			if(ObjectUtils.isEmpty(categoryDto.getIsactive())){
				error.put("isActive", "isActive field is empty or null");
			}
			else {
				if(categoryDto.getIsactive()  != Boolean.TRUE.booleanValue() && categoryDto.getIsactive() != Boolean.FALSE.booleanValue() ) {
					error.put("isActive", "invalid value isActive field ");
				}
				
			}
			
		}
		if(!error.isEmpty()) {
			throw new ValidationException(error);
			
		}
		
	}
}
 