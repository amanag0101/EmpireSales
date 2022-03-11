package com.es.empiresales.repository;

import com.es.empiresales.entity.Address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
    
}
