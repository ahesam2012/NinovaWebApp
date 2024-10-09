package com.example.po_app.model;

import lombok.Data;
import jakarta.persistence.*;

import java.util.List;

@Data
@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_order_id")
    private List<OrderItem> items;

    private Long createdBy;  // User who created the PO

    public List<OrderItem> getItems() {
        return this.items;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Long getId() {
        return this.id;
    }
}
