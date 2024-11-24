package com.pratik.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pratik.dto.NotesDto;
import com.pratik.dto.NotesDto.CategoryDto;
import com.pratik.entity.Notes;
import com.pratik.exception.ResourceNotFoundException;
import com.pratik.repository.CategoryRepository;
import com.pratik.repository.NotesRepository;
import com.pratik.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {
	
	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException {
		// Category validation 
		checkCategoryExist(notesDto.getCategory());
		
		Notes notes = mapper.map(notesDto, Notes.class);
		
		Notes saveNotes = notesRepo.save(notes);
		
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}

	private void checkCategoryExist(CategoryDto category) throws ResourceNotFoundException {
		categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("category id invalid"));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
	
		return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
	}
	
	
}
