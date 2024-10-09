package com.example.po_app.controller;

import com.example.po_app.model.PurchaseOrder;
import com.example.po_app.service.PDFService;
import com.example.po_app.service.PurchaseOrderService;
import com.example.po_app.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/po")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PDFService pdfService;

    @PostMapping("/create")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrder po) {
        try {
            PurchaseOrder newPO = purchaseOrderService.createPurchaseOrder(po);
            return ResponseEntity.ok(new CustomResponse<>("success", 200, null, null, LocalDateTime.now(), newPO, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>("error", 500, "Create Error", e.getMessage(), LocalDateTime.now(), null, false));
        }
    }

    @PostMapping("/create-pdf")
    public ResponseEntity<byte[]> createPdf(@RequestBody PurchaseOrder po) {
        System.out.println("Received request to create PDF for purchase order: " + po);  // Log received data
        try {
            byte[] pdfBytes = pdfService.generatePOPDF(po);
            if (pdfBytes == null) {
                System.out.println("PDF generation failed - returning error response.");  // Log if PDF generation fails
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "purchase-order.pdf");
    
            System.out.println("PDF generated successfully - sending response.");  // Log if PDF is generated successfully
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception caught while generating PDF: " + e.getMessage());  // Log the exception details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    
} 