// src/main/java/com/example/po_app/controller/OrderController.java

package com.example.po_app.controller;

import com.example.po_app.model.Order;
import com.example.po_app.model.User;
import com.example.po_app.response.CreateOrderResponse;
import com.example.po_app.response.GetAllOrdersResponse;
import com.example.po_app.response.GetUserOrdersResponse;
import com.example.po_app.service.OrderService;
import com.example.po_app.service.PDFService;
import com.example.po_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PDFService pdfService;

    // Create a new order
    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody Order order, Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new CreateOrderResponse(
                    "error",
                    HttpStatus.UNAUTHORIZED.value(),
                    "User not found",
                    null,
                    LocalDateTime.now(),
                    false
                )
            );
        }

        order.setUser(currentUser);
        order.setOrderStatus("PENDING");

        // Calculate order subtotal (if not provided)
        if (order.getOrderSubtotal() == null) {
            // Implement subtotal calculation if needed
        }

        Order newOrder = orderService.createOrder(order);
        return ResponseEntity.ok(
            new CreateOrderResponse(
                "success",
                HttpStatus.OK.value(),
                null,
                newOrder,
                LocalDateTime.now(),
                true
            )
        );
    }

    // Generate PDF for an order
    @GetMapping("/create-pdf/{orderId}")
    public ResponseEntity<byte[]> createPdf(@PathVariable Long orderId, Authentication authentication) {
        try {
            Order order = orderService.getOrderById(orderId);

            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Check if the user has access to the order
            String username = authentication.getName();
            User currentUser = userService.findByUsername(username);

            if (currentUser == null || (!currentUser.getRole().equals("ADMIN") && !order.getUser().getId().equals(currentUser.getId()))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            byte[] pdfBytes = pdfService.generatePOPDF(order);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "purchase-order-" + orderId + ".pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get all orders (accessible by admins)
    @GetMapping("/all")
    public ResponseEntity<GetAllOrdersResponse> getAllOrders(Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new GetAllOrdersResponse(
                    "error",
                    HttpStatus.FORBIDDEN.value(),
                    "Access denied",
                    null,
                    LocalDateTime.now(),
                    false
                )
            );
        }

        List<Order> orders = orderService.getAllOrders();

        return ResponseEntity.ok(
            new GetAllOrdersResponse(
                "success",
                HttpStatus.OK.value(),
                null,
                orders,
                LocalDateTime.now(),
                true
            )
        );
    }

    // Get user-specific orders
    @GetMapping("/user-orders")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GetUserOrdersResponse> getUserOrders(Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new GetUserOrdersResponse(
                    "error",
                    HttpStatus.UNAUTHORIZED.value(),
                    "User not found",
                    null,
                    LocalDateTime.now(),
                    false
                )
            );
        }

        List<Order> orders = orderService.getOrdersByUser(currentUser);

        return ResponseEntity.ok(
            new GetUserOrdersResponse(
                "success",
                HttpStatus.OK.value(),
                null,
                orders,
                LocalDateTime.now(),
                true
            )
        );
    }
}
