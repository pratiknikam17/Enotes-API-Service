package com.pratik.service;

import java.util.List;

import com.pratik.dto.NotesDto;

public interface NotesService {
	
	public Boolean saveNotes(NotesDto notesDto) throws Exception;
	
	public List<NotesDto> getAllNotes();

}
