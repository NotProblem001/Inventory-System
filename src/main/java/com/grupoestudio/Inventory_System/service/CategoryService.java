package com.grupoestudio.Inventory_System.service;

import com.grupoestudio.Inventory_System.model.Category;
import com.grupoestudio.Inventory_System.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findallCategory(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
    }

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }

    public boolean hasLowStock(Long categoryId, int minStock) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
    
        return category.getProducts().stream().anyMatch(product -> product.getStock() < minStock);
    }
}
