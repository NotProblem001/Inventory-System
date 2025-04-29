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

    public Product findByIdProduct(Long idProduct){
        return productRepository.findById(idProduct).orElseThrow(()-> new RuntimeException("Product not found"));
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteByIdProduct(Long idProduct){
        productRepository.deleteById(idProduct);
    }
}
