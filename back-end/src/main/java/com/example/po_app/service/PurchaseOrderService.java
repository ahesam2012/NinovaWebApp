package com.example.po_app.service;

import com.example.po_app.model.PurchaseOrder;
import com.example.po_app.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @jakarta.transaction.Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrder po) {
        return purchaseOrderRepository.save(po);
    }

    // Add more methods for fetching, updating, and deleting POs.
}
