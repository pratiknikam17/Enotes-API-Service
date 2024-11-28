package com.pratik.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pratik.dto.NotesDto;

public interface NotesService {
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();



}
