package com.boot.service;

import org.springframework.stereotype.Service;

import com.boot.entity.Product;
import com.boot.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    /**
     * For saving a product in the database we will use the following code:
     */
    public Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to save product: " + e.getMessage());
        }
    }
    //To get all products from the database we will use the following code:
    public List <Product> fetchAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to fetch all products: " + e.getMessage());
        }
    }
    // Get a product by ID
   public Optional<Product> fetchProductById(Long id) {
    try {
        return productRepository.findById(id);
    } catch (Exception e) {
        // Handle exception or log the error
        throw new RuntimeException("Failed to fetch product by ID: " + e.getMessage());
    }
}
//For updating a single product from the database we will use the following code:
public Optional<Product> updateProduct(Long id, Product updatedProduct) {
    try {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            Product savedEntity = productRepository.save(existingProduct);
            return Optional.of(savedEntity);
        } else {
            return Optional.empty();
        }
    } catch (Exception e) {
        // Handle exception or log the error
        throw new RuntimeException("Failed to update product: " + e.getMessage());
    }
}
//For deleting a single product from the database we will use the following code:
public boolean deleteProduct(Long id) {
    try {
        productRepository.deleteById(id);
        return true; // Deletion successful
    } catch (Exception e) {
        // Handle exception or log the error
        throw new RuntimeException("Failed to delete product: " + e.getMessage());
    }
}

}
