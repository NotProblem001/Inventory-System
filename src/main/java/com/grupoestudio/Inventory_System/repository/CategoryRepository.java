package com.grupoestudio.Inventory_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grupoestudio.Inventory_System.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
