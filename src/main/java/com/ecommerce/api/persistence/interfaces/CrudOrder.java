package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Orders;
import java.time.*;
import java.util.*;
public interface CrudOrder {
    void save(Orders order);
    Orders findById(String id);
    void update(Orders orders, Long id);
    void delete(String id);
    Iterable<Orders> findAll();
    Iterable<Orders> findByUserId(List<String> userId);
    Iterable<Orders> findByStatus(List<String> status);
    Iterable<Orders> findByOrderDate(List<LocalDateTime> orderDate);
}
