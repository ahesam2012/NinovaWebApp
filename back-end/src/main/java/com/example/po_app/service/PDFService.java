package com.example.po_app.service;

import com.example.po_app.model.PurchaseOrder;
import com.example.po_app.model.OrderItem;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PDFService {

    public byte[] generatePOPDF(PurchaseOrder po) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Purchase Order Details:"));
            document.add(new Paragraph("Order ID: " + po.getId()));
            document.add(new Paragraph("Customer: " + po.getCustomerName()));

            // Create a table for item details
            float[] columnWidths = {200F, 100F, 100F, 100F};
            Table table = new Table(columnWidths);
            table.addCell("Item");
            table.addCell("Quantity");
            table.addCell("Unit Price");
            table.addCell("Total Price");

            for (OrderItem item : po.getItems()) {
                table.addCell(item.getItem());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getUnitPrice()));
                table.addCell(String.valueOf(item.getTotalPrice()));
            }

            document.add(table);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
