package com.potilati.productService.controller;

import com.potilati.productService.DTO.ProductDTO;
import com.potilati.productService.model.Product;
import com.potilati.productService.repository.ProductRepository;
import com.potilati.productService.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO){
        if (productDTO.getReference()==0 || productDTO.getReference() < 10000){
            return ResponseEntity.badRequest().body("Error: reference is null or invalid!");
        }
        if (productRepository.existsByReference(productDTO.getReference())){
            return ResponseEntity.badRequest().body("Error: reference is already taken!");
        }
        Product product;
        try {
             product = productService.createProduct(productDTO);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("product is not created ...!" + e, HttpStatus.BAD_REQUEST);
        }
        logger.info("product {} with reference {} is created", product.getName(), product.getReference());
        return ResponseEntity.ok(product);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getALl(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Product product;
        try {
            product = productService.getProductById(id);
        } catch (RuntimeException e){
            logger.info("No product with id {} :: error : "+ e,id);
            return new ResponseEntity<>("No product with id " +id  , HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(product);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        if (!productRepository.existsProductById(id)){
            logger.info("No product with id {} :: error : product not found !",id);
            return new ResponseEntity<>("No product with id " +id  , HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getReference()==0 || productDTO.getReference() < 10000){
            logger.info("error modification :: reference {} is null or invalid!", productDTO.getReference());
            return ResponseEntity.badRequest().body("Error: reference is null or invalid!");
        }
        if ((productRepository.findProductsByReference(productDTO.getReference()).size()) > 1){
            logger.info("error modification :: reference {} is already taken!", productDTO.getReference());
            return ResponseEntity.badRequest().body("Error: reference is already taken!");
        }
        Product product;
        try {
            product = productService.modifyProduct(id, productDTO);
        } catch (RuntimeException e){
            logger.info("error modification : product with id {} :: error : "+ e,id);
            return new ResponseEntity<>("error modification : product with id " +id  , HttpStatus.BAD_REQUEST);
        }
        logger.info("product \"{}\" with reference \"{}\" is modified", product.getName(), product.getReference());
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (!productRepository.existsProductById(id)){
            logger.info("No product with id {} :: error : product not found !",id);
            return new ResponseEntity<>("No product with id " +id  , HttpStatus.BAD_REQUEST);
        }
        String deleteResponse;

        try {
            deleteResponse = productService.deleteProduct(id);
        } catch (RuntimeException e){
            logger.info("error delete : product with id {} :: error : "+ e,id);
            return new ResponseEntity<>("error delete : product with id " +id  , HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(deleteResponse);
    }

}
