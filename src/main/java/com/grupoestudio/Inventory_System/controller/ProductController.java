package com.grupoestudio.Inventory_System.controller;

import java.util.List;

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

import com.grupoestudio.Inventory_System.model.Product;
import com.grupoestudio.Inventory_System.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listAllProduct(){
        List<Product> products = productService.findallProducts();
        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product newProduct){
        Product product = productService.saveProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @GetMapping("/{idProduct}")
    public ResponseEntity<Product> findByIdProduct(@PathVariable Long idProduct){
        try {
            Product product = productService.findByIdProduct(idProduct);
            return ResponseEntity.ok(product);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idProduct}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long idProduct, @RequestBody Product product){
        try{
            Product existingProduct = productService.findByIdProduct(idProduct);
            product.setIdProduct(idProduct);
            Product updateProduct = productService.saveProduct(product);
            return ResponseEntity.ok(updateProduct);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idProduct}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long idProduct){
        try {
            productService.deleteByIdProduct(idProduct);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
