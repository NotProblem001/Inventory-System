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

    public Category findByIdCategory(Long idCategory){
        return categoryRepository.findById(idCategory).orElseThrow(()-> new RuntimeException("Category not found"));
    }

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteByIdCategory(Long idCategory){
        categoryRepository.deleteById(idCategory);
    }

    
}
