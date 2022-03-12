package com.es.empiresales.repository;

import java.util.List;

import com.es.empiresales.entity.Cart;
import com.es.empiresales.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepo extends JpaRepository<Cart, Long> {
    @Query(value = "select * from Cart c where c.user_id =:user and c.product_id =:productId", nativeQuery = true)
    public Cart getByUserAndProductId(@Param("user") User user, @Param("productId") Long productId);

    @Query(value = "select sum(quantity) from Cart c where c.user_id =:user", nativeQuery = true)
    public Integer getCountOfItems(User user);

    public List<Cart> findByUser(User user);
}
