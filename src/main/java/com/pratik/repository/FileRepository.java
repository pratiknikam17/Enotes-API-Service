package com.pratik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratik.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {
	
}
