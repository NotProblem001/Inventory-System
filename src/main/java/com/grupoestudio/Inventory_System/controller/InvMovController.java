package com.grupoestudio.Inventory_System.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoestudio.Inventory_System.model.InventoryMovement;
import com.grupoestudio.Inventory_System.service.InvMovService;

@RestController
@RequestMapping("/api/v1/InvMov")
public class InvMovController {

    @Autowired
    private InvMovService invMovService;

    @GetMapping
    public ResponseEntity<List<InventoryMovement>> listAllInvMov(){
        List<InventoryMovement> inventoryMovements = invMovService.findallInvMov();
        return inventoryMovements.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(inventoryMovements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovement> findByIdInvMov(@PathVariable Long id){
        try {
            InventoryMovement inventoryMovement = invMovService.findById(id);
            return ResponseEntity.ok(inventoryMovement);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
