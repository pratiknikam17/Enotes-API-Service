package com.pratik.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratik.dto.FavouriteNoteDto;
import com.pratik.dto.NotesDto;
import com.pratik.dto.NotesDto.CategoryDto;
import com.pratik.dto.NotesDto.FilesDto;
import com.pratik.dto.NotesResponse;
import com.pratik.entity.FavouriteNote;
import com.pratik.entity.FileDetails;
import com.pratik.entity.Notes;
import com.pratik.exception.ResourceNotFoundException;
import com.pratik.repository.CategoryRepository;
import com.pratik.repository.FavouriteNoteRepository;
import com.pratik.repository.FileRepository;
import com.pratik.repository.NotesRepository;
import com.pratik.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {
	
	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private FavouriteNoteRepository favouriteNoteRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Autowired
	private FileRepository fileRepo;
	
	

	@Override
	public Boolean saveNotes(String notes,MultipartFile file) throws Exception {
		
		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class); 
		
		notesDto.setIsDeleted(false);
		notesDto.setDeletedOn(null);
		
		
		// update notes if id is given in request
		if(!ObjectUtils.isEmpty(notesDto.getId())) {
			updateNotes(notesDto,file);
		}
		
		// Category validation 
		checkCategoryExist(notesDto.getCategory());
		
		Notes notesMap = mapper.map(notesDto, Notes.class);
		
		FileDetails fileDtls=saveFileDetails(file);
		
		if(!ObjectUtils.isEmpty(fileDtls)) {
			notesMap.setFileDetails(fileDtls);
		} else {
			
			if(ObjectUtils.isEmpty(notesDto.getId())) {
				notesMap.setFileDetails(null);
			}
			
		}
		
		Notes saveNotes = notesRepo.save(notesMap);
		
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}

	private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
		
		Notes existNotes=notesRepo.findById(notesDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Invalid Notes id"));
		
		// user not choose any file at update time
		if(ObjectUtils.isEmpty(file)) {
			notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), FilesDto.class));
		}
		
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		
		if(!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			
			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);
			
			
			List<String> extensionAllow = Arrays.asList("pdf","xlsx","jpg","png","docx");	
			if(!extensionAllow.contains(extension)) {
				throw new IllegalArgumentException("invalid file format! Upload only .pdf, .xlsx , .jpg");
			}
						
			
			String rndString=UUID.randomUUID().toString();
			String uploadfileName = rndString+"."+extension;
			
						
			File saveFile = new File(uploadPath);
			if(!saveFile.exists()) {
				saveFile.mkdir();	
				}
			//path: enotesapiservice/notes/java.pdf
			String storePath = uploadPath.concat(uploadfileName);
			
			
			
			//upload file
			long upload=Files.copy(file.getInputStream(), Paths.get(storePath));
			if(upload!=0) {
				FileDetails fileDtls = new FileDetails();
				fileDtls.setOriginalFileName(originalFilename);
				fileDtls.setDisplayFileName(getDisplayName(originalFilename));
				fileDtls.setUploadFileName(uploadfileName);
				fileDtls.setFileSize(file.getSize());
				fileDtls.setPath(storePath);
				FileDetails saveFileDtls=fileRepo.save(fileDtls);
				 return saveFileDtls;
			}
		}
		
		return null;
	}

	private String getDisplayName(String originalFileName) {
		//java programming tutorails.pdf
		 String extension =FilenameUtils.getExtension(originalFileName);
		 String fileName = FilenameUtils.removeExtension(originalFileName);
		 
		 if(fileName.length() > 8) {
			  fileName = fileName.substring(0,7);
		 }
		 fileName=fileName + "." + extension;
		 return fileName;
		
	}

	private void checkCategoryExist(CategoryDto category) throws ResourceNotFoundException {
		categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("category id invalid"));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
	
		return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		
			
		InputStream io=new FileInputStream(fileDetails.getPath());
		
		return StreamUtils.copyToByteArray(io);
		
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDtls=fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("File is not available"));
		
		return fileDtls;
	}

	@Override
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
		
		Pageable pageable =PageRequest.of(pageNo, pageSize);
		Page<Notes> pageNotes =notesRepo.findByCreatedbyAndIsDeletedFalse(userId,pageable);
		 
		List<NotesDto> notesDto=pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();
		
		NotesResponse notes = NotesResponse.builder()
				.notes(notesDto)
				.pageNo(pageNotes.getNumber())
				.pageSize(pageNotes.getSize())
				.totalElements(pageNotes.getTotalElements())
				.totalPages(pageNotes.getTotalPages())
				.isFirst(pageNotes.isFirst())
				.isLast(pageNotes.isLast())
				.build();
				
		
		return notes;
	}

	@Override
	public void softDeleteNotes(Integer id) throws Exception {
		
		Notes notes = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes id invalid ! Not Found"));
		
		notes.setIsDeleted(true);
		notes.setDeletedOn(LocalDateTime.now());
		notesRepo.save(notes);
		
		
	}

	@Override
	public void restoreNotes(Integer id) throws Exception {
		Notes notes = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes id invalid ! Not Found"));
		
		notes.setIsDeleted(false);
		notes.setDeletedOn(null);
		notesRepo.save(notes);
		
	}

	@Override
	public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
		List<Notes> recycleNotes=notesRepo.findByCreatedbyAndIsDeletedTrue(userId);
		List<NotesDto> notesDtoList = recycleNotes.stream().map(note -> mapper.map(note, NotesDto.class)).toList();
		return notesDtoList;
	}

	@Override
	public void hardDeleteNotes(Integer id) throws Exception {
		Notes notes=notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes not found"));
		
		if(notes.getIsDeleted()) {
			notesRepo.delete(notes);
		} else {
			throw new IllegalArgumentException("Sorry you can't hard delete Directly");
			
		}
	}

	@Override
	public void emptyRecycleBin(int userId) {
		List<Notes> recycleNotes=notesRepo.findByCreatedbyAndIsDeletedTrue(userId);
		if(!CollectionUtils.isEmpty(recycleNotes)) {
			notesRepo.deleteAll(recycleNotes);
		}
		
	}

	@Override
	public void favouriteNotes(Integer noteId) throws Exception {
		int userId=2;
		
		Notes notes=notesRepo.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Notes not found & id is Invalid"));
		FavouriteNote favouriteNote=FavouriteNote.builder()
				.note(notes)
				.userId(userId)
				.build();
		favouriteNoteRepo.save(favouriteNote);
		
		
	}

	@Override
	public void unfavouriteNotes(Integer favouriteNoteId) throws Exception {
		FavouriteNote favnote=favouriteNoteRepo.findById(favouriteNoteId).orElseThrow(() -> new ResourceNotFoundException("Favourite Notes not found & id is Invalid"));
		favouriteNoteRepo.delete(favnote);
		
	}

	@Override
	public List<FavouriteNoteDto> getUserFavouriteNotes() throws Exception {
		int userId=2;
		List<FavouriteNote> favouriteNotes = favouriteNoteRepo.findByUserId(userId);	
		return favouriteNotes.stream().map(fn -> mapper.map(fn, FavouriteNoteDto.class)).toList();
		 
	}

	@Override
	public Boolean copyNotes(Integer id) throws Exception {
		Notes notes = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes id invalid ! Not Found"));
		
//		Notes copyNote=new Notes();
//		copyNote.setTitle(notes.getTitle());
		
		Notes copNote = Notes.builder()
				.title(notes.getTitle())
				.description(notes.getDescription())
				.category(notes.getCategory())
				.isDeleted(false)
				.fileDetails(null)
				.build();
		
		//TODO : Need to check user Validation
		Notes saveCopyNote= notesRepo.save(copNote);
		if(!ObjectUtils.isEmpty(saveCopyNote)) {
			return true;
		}
		return false;
	}
	
	
	
}
