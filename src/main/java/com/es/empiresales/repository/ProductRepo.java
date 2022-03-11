package com.es.empiresales.repository;

import com.es.empiresales.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query(value = "select count(id) from product", nativeQuery = true)
    public Integer getProductCount();
}
