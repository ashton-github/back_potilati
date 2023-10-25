package com.potilati.productService.repository;

import com.potilati.productService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Boolean existsByReference(Double reference);
    Boolean existsProductById(Long id);
    List<Product> findProductsByReference(Double reference);
}
