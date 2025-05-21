package com.grupoestudio.Inventory_System.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventoryMovement")
@Data
@NoArgsConstructor
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @Column(nullable = false)
    private int quantity;
    
    @Column(nullable = false)
    private String type; //entrada o salida de stock lol

    @Column(nullable = false)
    private Date date;

    public void createInvMov(Product product, int quantity, String type, Date date){

        this.product = product;
        this.quantity = quantity;
        this.type = type;
        this.date = new Date();
    }

}
