package com.pratik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratik.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

}
