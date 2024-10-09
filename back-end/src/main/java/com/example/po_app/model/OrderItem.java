package com.example.po_app.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;
    private int quantity;
    private double unitPrice;

    // Method to calculate the total price for this item
    public double getTotalPrice() {
        return this.quantity * this.unitPrice;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public String getItem(){
        return this.item;
    }
}
