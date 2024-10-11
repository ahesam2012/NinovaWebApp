package com.example.po_app.repository;

import com.example.po_app.model.Order;
import com.example.po_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus(String orderStatus);
    List<Order> findByUserId(Long userId);
    List<Order> findByUser(User user);
    List<Order> findAll();
}
