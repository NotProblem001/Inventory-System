package com.grupoestudio.Inventory_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grupoestudio.Inventory_System.model.InventoryMovement;

public interface InvMovRepository extends JpaRepository<InventoryMovement,Long> {

}
