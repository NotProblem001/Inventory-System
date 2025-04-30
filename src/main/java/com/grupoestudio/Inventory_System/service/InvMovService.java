package com.grupoestudio.Inventory_System.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoestudio.Inventory_System.model.InventoryMovement;
import com.grupoestudio.Inventory_System.repository.InvMovRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InvMovService {

    @Autowired
    private InvMovRepository invMovRepository;

    public List<InventoryMovement> findallInvMov(){
        return invMovRepository.findAll();
    }

    public InventoryMovement findById(Long id){
        return invMovRepository.findById(id).orElseThrow(()-> new RuntimeException("Inventory Movement not found"));
    }

    public InventoryMovement saveInvMov(InventoryMovement InvMov){
        return invMovRepository.save(InvMov);
    }

    public void deleteById(Long id){
        invMovRepository.deleteById(id);
    }


}
