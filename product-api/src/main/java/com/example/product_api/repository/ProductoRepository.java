package com.example.product_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product_api.model.Product;

@Repository
public interface ProductoRepository extends JpaRepository<Product, Long> {

}
