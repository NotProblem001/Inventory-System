package com.grupoestudio.Inventory_System.service;

import com.grupoestudio.Inventory_System.model.Product;
import com.grupoestudio.Inventory_System.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> findallProducts(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public double calculateTotalValue(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return product.getPrice() * product.getStock();
    }
}
