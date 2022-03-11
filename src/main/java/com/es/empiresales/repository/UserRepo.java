package com.es.empiresales.repository;

import com.es.empiresales.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public User findByPhone(String phone);

    @Query(value = "select count(id) from user", nativeQuery = true)
    public Integer getUserCount();
}
