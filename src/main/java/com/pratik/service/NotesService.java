package com.pratik.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pratik.dto.FavouriteNoteDto;
import com.pratik.dto.NotesDto;
import com.pratik.dto.NotesResponse;
import com.pratik.entity.FavouriteNote;
import com.pratik.entity.FileDetails;

public interface NotesService {
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDtls) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;

	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDto> getUserRecycleBinNotes(Integer userId);

	public void hardDeleteNotes(Integer id) throws Exception;

	public void emptyRecycleBin(int userId);

	public void favouriteNotes(Integer noteId) throws Exception;
	
	public void unfavouriteNotes(Integer noteId ) throws Exception;
	
	public List<FavouriteNoteDto> getUserFavouriteNotes() throws Exception;

	public Boolean copyNotes(Integer id) throws Exception;

}
