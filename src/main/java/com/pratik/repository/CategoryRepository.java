package com.pratik.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratik.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


	List<Category> findByIsactiveTrueAndIsdeletedFalse();

	Optional<Category> findByIdAndIsdeletedFalse(Integer id);

	List<Category> findByIsdeletedFalse();

	Boolean existsByName(String name);

}
 