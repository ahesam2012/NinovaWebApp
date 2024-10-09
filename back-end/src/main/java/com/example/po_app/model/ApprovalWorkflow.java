package com.example.po_app.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class ApprovalWorkflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long poId; // Reference to PurchaseOrder
    private int approvalLevel;
    private Long assignedToUser;
    private String status;
}
