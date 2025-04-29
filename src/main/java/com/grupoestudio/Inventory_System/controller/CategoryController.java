package com.grupoestudio.Inventory_System.controller;

import com.grupoestudio.Inventory_System.model.Category;
import com.grupoestudio.Inventory_System.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> listAllCategories(){
        List<Category> categories = categoryService.findallCategory();
        return categories.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categories); //operador ternario
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category newCategory){
        Category category = categoryService.saveCategory(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<Category> findByIdCategory(@PathVariable Long idCategory){
        try {
            Category category = categoryService.findByIdCategory(idCategory);
            return ResponseEntity.ok(category);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idCategory}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long idCategory, @RequestBody Category category){
        try{
            Category existingCategory = categoryService.findByIdCategory(idCategory);
            category.setIdCategory(idCategory);
            Category updateCategory = categoryService.saveCategory(category);
            return ResponseEntity.ok(updateCategory);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idCategory}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long idCategory){
        try {
            categoryService.deleteByIdCategory(idCategory);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

