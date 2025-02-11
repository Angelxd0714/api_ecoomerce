package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.dto.response.OrdersDTO;
import com.ecommerce.api.persistence.entities.Orders;
import java.time.*;
import java.util.*;
public interface CrudOrder {
    void save(OrdersRequest order);
    OrdersDTO findById(Long id);
    void update(OrdersRequest orders, Long id);
    void delete(Long id);
    List<OrdersDTO> findAll();
    List<OrdersDTO> findByUserId(List<Long> userId);
    List<OrdersDTO> findByStatus(String status);
    List<OrdersDTO> findByOrderDate(List<LocalDateTime> orderDate);
}
