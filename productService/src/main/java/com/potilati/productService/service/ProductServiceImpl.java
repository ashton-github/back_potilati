package com.potilati.productService.service;

import com.potilati.productService.DTO.ProductDTO;
import com.potilati.productService.model.Product;
import com.potilati.productService.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setReference(productDTO.getReference());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new RuntimeException(" product not found !"));
    }

    @Override
    public Product modifyProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id).map(p->{
            if (productDTO.getReference()!=0){
                p.setReference(productDTO.getReference());
            }
            if (!productDTO.getName().isEmpty()){
                p.setName(productDTO.getName());
            }
            if (!productDTO.getDescription().isEmpty()){
                p.setDescription(productDTO.getDescription());
            }
            return productRepository.save(p);
        }).orElseThrow(()->new RuntimeException("exception"));
    }

    @Override
    public String deleteProduct(Long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.deleteById(id);
            return "product deleted successfully";
        }).orElseThrow(()->new RuntimeException("product id not found"));
    }
}
