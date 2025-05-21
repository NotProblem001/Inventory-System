package com.grupoestudio.Inventory_System.service;

import com.grupoestudio.Inventory_System.model.Category;
import com.grupoestudio.Inventory_System.model.InventoryMovement;
import com.grupoestudio.Inventory_System.model.Product;
import com.grupoestudio.Inventory_System.repository.CategoryRepository;
import com.grupoestudio.Inventory_System.repository.InvMovRepository;
import com.grupoestudio.Inventory_System.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

                ajuste.createInvMov(product, diference, "AJUSTE",new Date());

                invMovRepository.save(ajuste);
                
            }
            productRepository.save(product);
        }
    }

    public void incrementStock(Long id, int quantity){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> products = category.getProducts();

        for(Product product : products){
            int stockActual = product.getStock();
            
            if(product.isActive() == false){
                throw new RuntimeException("Error, the product is not active");
            }else {
                int changes = stockActual + quantity;

                InventoryMovement ajuste = new InventoryMovement();

                ajuste.createInvMov(product, changes, "INCREMENTO",new Date());

                invMovRepository.save(ajuste);
            }
            productRepository.save(product);
        }
    }

    public void transferStock(Long sourceCategoryId, Long targetCategoryId, int quantity) {
        Category sourceCategory = categoryRepository.findById(sourceCategoryId).orElseThrow(() -> new RuntimeException("Source category not found"));
        Category targetCategory = categoryRepository.findById(targetCategoryId).orElseThrow(() -> new RuntimeException("Target category not found"));

        List<Product> sourceProducts = sourceCategory.getProducts();
        List<Product> targetProducts = targetCategory.getProducts();

        for (Product targetProduct : targetProducts) {
            if (targetProduct.isActive() == false) {
                throw new RuntimeException("Error, the product is not active");
            } else if (targetProduct.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock in target product");
            } else if (quantity <= 0) {
                throw new RuntimeException("Quantity must be greater than zero");
            }
        }
        for (Product sourceProduct : sourceProducts) {
            if(sourceProduct.isActive() == false) {
                throw new RuntimeException("Error, the product is not active");
            }else if (sourceProduct.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock in source product");

            } else if (quantity <= 0) {
                throw new RuntimeException("Quantity must be greater than zero");
            }

            InventoryMovement exitMovement = new InventoryMovement();
            exitMovement.createInvMov(sourceProduct, quantity, "TRANSFERENCIA", new Date());
            invMovRepository.save(exitMovement);

            for (Product targetProduct : targetProducts) {
                targetProduct.setStock(targetProduct.getStock() + quantity);
                productRepository.save(targetProduct);
            }
        }
    }

    /* Crear un metodo en categoryService que transfiera una cantidad de stock desde un producto origen a un producto destino, ambos en la misma categoria
    validar los id de origen y destino
    validar el stock del origen tenga suficiente cantidad y que ambos esten activos
    crear los 2 mov de inventario
    guardar los procedimientos
    metodo tiene que ser booleano
    retorna true si es que la transferencia fue exitosa y false si no (por ejemplo que no hay stock suficiente) */

}
