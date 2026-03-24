package com.irfan.springmvc.repository;

import com.irfan.springmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
