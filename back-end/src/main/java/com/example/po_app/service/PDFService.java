package com.example.po_app.service;

import com.example.po_app.model.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class PDFService {

    public byte[] generatePOPDF(Order order) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add title
            document.add(new Paragraph("Purchase Order Details").setBold().setFontSize(18));

            // Add order information
            document.add(new Paragraph("Order ID: " + order.getId()));
            document.add(new Paragraph("Order Date: " + order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            document.add(new Paragraph("User ID: " + order.getUser().getId()));

            // Parse items from JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> items = objectMapper.readValue(order.getItems(), new TypeReference<List<Map<String, Object>>>(){});

            // Create a table for item details
            float[] columnWidths = {200F, 100F, 100F};
            Table table = new Table(columnWidths);
            table.addHeaderCell(new Cell().add(new Paragraph("Item").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Quantity").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Unit Price").setBold()));

            for (Map<String, Object> item : items) {
                table.addCell(new Cell().add(new Paragraph((String) item.get("item"))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.get("quantity")))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.get("unitPrice")))));
            }

            document.add(table);

            // Add subtotal and status
            document.add(new Paragraph("Order Subtotal: $" + order.getOrderSubtotal()));
            document.add(new Paragraph("Order Status: " + order.getOrderStatus()));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
