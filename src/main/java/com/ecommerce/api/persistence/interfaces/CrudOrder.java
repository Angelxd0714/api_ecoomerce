package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Order;
import java.time.*;
import java.util.*;
public interface CrudOrder {
    void save(Order order);
    Order findById(String id);
    void update(Order order, Long id);
    void delete(String id);
    Iterable<Order> findAll();
    Iterable<Order> findByUserId(List<String> userId);
    Iterable<Order> findByStatus(List<String> status);
    Iterable<Order> findByOrderDate(List<LocalDateTime> orderDate);
}
