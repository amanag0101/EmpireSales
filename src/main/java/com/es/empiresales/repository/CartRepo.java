package com.es.empiresales.repository;

import com.es.empiresales.entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
    
}
