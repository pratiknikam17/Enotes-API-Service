package com.pratik.controller;

import java.util.List;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pratik.dto.NotesDto;
import com.pratik.dto.NotesResponse;
import com.pratik.entity.FileDetails;
import com.pratik.service.NotesService;
import com.pratik.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception{
		 Boolean saveNotes = notesService.saveNotes(notes,file);
		 if(saveNotes) {
			return CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED); 
		 }
		 return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		
		FileDetails fileDetails=notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		
		HttpHeaders headers=new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		
		
		return ResponseEntity.ok().headers(headers).body(data);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes(){
		 List<NotesDto> notes = notesService.getAllNotes();
		 if(CollectionUtils.isEmpty(notes)) {
			 return ResponseEntity.noContent().build();
		 }
		 return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@GetMapping("/user-notes")
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name="pageNo",defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize){
		Integer userId=2;
		 NotesResponse notes = notesService.getAllNotesByUser(userId,pageNo,pageSize);
//		 if(CollectionUtils.isEmpty(notes)) {
//			 return ResponseEntity.noContent().build();
//		 }
		 return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
		
		notesService.softDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delete success", HttpStatus.OK);
		
	}
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
		 
		notesService.restoreNotes(id);
		return CommonUtil.createBuildResponseMessage("Restore success", HttpStatus.OK);
	}
	
	@GetMapping("/recycle-bin")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
		Integer userId=2;
		List<NotesDto> notes=notesService.getUserRecycleBinNotes(userId);
		if(CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("Notes not available in Recycle Bin", HttpStatus.OK);
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
}
 