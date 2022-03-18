package com.es.empiresales.repository;

import java.util.List;

import com.es.empiresales.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query(value = "select count(id) from product", nativeQuery = true)
    public Integer getProductCount();

    @Query("from Product as p where p.category.name =:categoryName")
    public Page<Product> findAllByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    // search query
    public List<Product> findByNameContaining(String name);
}
