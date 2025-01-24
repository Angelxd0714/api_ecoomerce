package com.ecommerce.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Order;
import com.ecommerce.api.persistence.interfaces.CrudOrder;
import com.ecommerce.api.persistence.repository.RepositoryOrder;

import java.time.*;
import java.util.*;
@Service
public class OrdersServices implements CrudOrder {
    @Autowired
    private RepositoryOrder repositoryOrder;


    @Override
    public void save(Order order) {
        repositoryOrder.save(order);
    }

    @Override
    public Order findById(String id) {
       return repositoryOrder.findById(id).orElse(null);
    }

    @Override
    public void update(Order order, Long id) {
       repositoryOrder.updateOrder(id, order);
    }

    @Override
    public void delete(String id) {
        repositoryOrder.deleteById(id);
    }

    @Override
    public Iterable<Order> findAll() {
       return repositoryOrder.findAll();
    }

    @Override
    public Iterable<Order> findByUserId(List<String> userId) {
       return (Iterable<Order>) repositoryOrder.findByUserId(userId);
    }

    @Override
    public Iterable<Order> findByStatus(List<String> status) {
       return (Iterable<Order>) repositoryOrder.findByStatus(status);
    }

    @Override
    public Iterable<Order> findByOrderDate(List<LocalDateTime> orderDate) {
        return (Iterable<Order>) repositoryOrder.findByOrderDate(orderDate);
    }
    
}
