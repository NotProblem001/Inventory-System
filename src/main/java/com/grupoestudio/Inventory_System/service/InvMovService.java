package com.grupoestudio.Inventory_System.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoestudio.Inventory_System.model.InventoryMovement;
import com.grupoestudio.Inventory_System.model.Product;
import com.grupoestudio.Inventory_System.repository.InvMovRepository;
import com.grupoestudio.Inventory_System.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InvMovService {

    @Autowired
    private InvMovRepository invMovRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<InventoryMovement> findallInvMov(){
        return invMovRepository.findAll();
    }

    public InventoryMovement findById(Long id){
        return invMovRepository.findById(id).orElseThrow(()-> new RuntimeException("Inventory Movement not found"));
    }

    
    public InventoryMovement save(InventoryMovement invMov){
        Product product = productRepository.findById(invMov.getProduct().getId()).get();
        /*Product productAsociado = invMov.getProduct();
        Long idProductoAsociado = productAsociado.getId();
        Product productAsociadoFinal = productRepository.findById(idProductoAsociado).get();*/
        int currentStock = product.getStock();
        if (invMov.getType().equals("ENTRADA")) {
            product.setStock(currentStock + invMov.getQuantity());
        }else if (invMov.getType().equals("SALIDA")) {
            if (currentStock < invMov.getQuantity()) {
                throw new RuntimeException("Te falta stock wn");
            }
            
            product.setStock(currentStock - invMov.getQuantity());
           
        }

        productRepository.save(product);
        return invMovRepository.save(invMov);
    }

    public void deleteById(Long id){
        invMovRepository.deleteById(id);
    }

    public void transferStock(Long sourceProductId, Long targetProductId, int quantity) {
        Product sourceProduct = productRepository.findById(sourceProductId).orElseThrow(() -> new RuntimeException("Source product not found"));
        Product targetProduct = productRepository.findById(targetProductId).orElseThrow(() -> new RuntimeException("Target product not found"));

        if (sourceProduct.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock in source product");
        }else if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        sourceProduct.setStock(sourceProduct.getStock() - quantity);
        targetProduct.setStock(targetProduct.getStock() + quantity);

        productRepository.save(sourceProduct);
        productRepository.save(targetProduct);
    }
}
