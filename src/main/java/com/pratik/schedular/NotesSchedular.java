package com.pratik.schedular;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pratik.entity.Notes;
import com.pratik.repository.NotesRepository;

@Component
public class NotesSchedular {
	
	@Autowired
	private NotesRepository notesRepo;
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNotesSchedular() {
//		System.out.println("i");
		LocalDateTime cutOfDate= LocalDateTime.now().minusDays(7);
		List<Notes> deleteNotes=notesRepo.findAllByIsDeletedAndDeletedOnBefore(true,cutOfDate);
		notesRepo.deleteAll(deleteNotes);
	}
}
