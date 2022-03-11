package com.es.empiresales.repository;

import com.es.empiresales.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    public Category findByName(String name);

    @Query(value = "select count(id) from category", nativeQuery = true)
    public Integer getCategoryCount();
}
