package com.grupoestudio.Inventory_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grupoestudio.Inventory_System.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
