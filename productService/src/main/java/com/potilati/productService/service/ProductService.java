package com.potilati.productService.service;

import com.potilati.productService.DTO.ProductDTO;
import com.potilati.productService.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct (ProductDTO product);

    List<Product> getAllProducts ();

    Product getProductById(Long id);

    Product modifyProduct(Long id, ProductDTO product);

    String deleteProduct(Long id);
}
