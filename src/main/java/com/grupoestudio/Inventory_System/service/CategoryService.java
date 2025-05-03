package com.grupoestudio.Inventory_System.service;

import com.grupoestudio.Inventory_System.model.Category;
import com.grupoestudio.Inventory_System.model.InventoryMovement;
import com.grupoestudio.Inventory_System.model.Product;
import com.grupoestudio.Inventory_System.repository.CategoryRepository;
import com.grupoestudio.Inventory_System.repository.InvMovRepository;
import com.grupoestudio.Inventory_System.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvMovRepository invMovRepository;

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

    public boolean hasLowStock(Long Id, int minStock) {
        Category category = categoryRepository.findById(Id).orElseThrow(() -> new RuntimeException("Category not found"));
    
        return category.getProducts().stream().anyMatch(product -> product.getStock() < minStock);
    }

    public void setPriceByPercentage(Long id, double percentage){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        
        List<Product> products = category.getProducts();

        for (Product product : products){
            double calculate = product.getPrice() * percentage;
            double finalPrice = product.getPrice() + calculate;
            product.setPrice(finalPrice);
        }
    }

    public int desactivateLowStockProducts(Long categoriId,int minStock){
        Category category = categoryRepository.findById(categoriId).orElseThrow(() -> new RuntimeException("Category not found"));
        List<Product> products = category.getProducts();
        int counter = 0;
        for (Product product : products){
            if (product.getStock() < minStock) {
                product.setActive(false);
                productRepository.save(product);
                counter ++;
            }             
        }
        return counter;
    }

    public void reassingProducts(Long sourceCategoryId, Long targetCategoryId){
        Category sourceCategory = categoryRepository.findById(sourceCategoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        Category targetCategory = categoryRepository.findById(targetCategoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> products = sourceCategory.getProducts();

        for(Product product : products){
            product.setCategory(targetCategory);
            productRepository.save(product);
        }
    }

    public void adjustStock(Long CategoryId, int maxStock){
        Category category = categoryRepository.findById(CategoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> products = category.getProducts();

        for(Product product : products){
            int stockActual = product.getStock();
            if (stockActual > maxStock) {
                int diference = stockActual - maxStock;
                product.setStock(maxStock);
                
                InventoryMovement ajuste = new InventoryMovement();
                ajuste.setProduct(product);
                ajuste.setType("AJUSTE");
                ajuste.setQuantity(diference);
                ajuste.setDate(new Date());

                invMovRepository.save(ajuste);
                
            }
            productRepository.save(product);
        }
    }
}
