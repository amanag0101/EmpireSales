package com.es.empiresales.rest;

import java.util.List;

import com.es.empiresales.entity.Product;
import com.es.empiresales.repository.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchRest {
    @Autowired private ProductRepo productRepo;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> getSearchResultFromQuery(@PathVariable("query") String query) {
        List<Product> productList = productRepo.findByNameContaining(query);
        return ResponseEntity.ok(productList);
    }
}
