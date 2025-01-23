package com.ecommerce.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.interfaces.CrudOrder;
import com.ecommerce.api.persistence.repository.RepositoryOrder;

import java.time.*;
import java.util.*;
@Service
public class OrdersServices implements CrudOrder {
    @Autowired
    private RepositoryOrder repositoryOrder;


    @Override
    public void save(Orders order) {
        repositoryOrder.save(order);
    }

    @Override
    public Orders findById(String id) {
       return repositoryOrder.findById(id).orElse(null);
    }

    @Override
    public void update(Orders order, Long id) {
       repositoryOrder.updateOrder(id, order);
    }

    @Override
    public void delete(String id) {
        repositoryOrder.deleteById(id);
    }

    @Override
    public Iterable<Orders> findAll() {
       return repositoryOrder.findAll();
    }

    @Override
    public Iterable<Orders> findByUserId(List<String> userId) {
       return (Iterable<Orders>) repositoryOrder.findByUserId(userId);
    }

    @Override
    public Iterable<Orders> findByStatus(List<String> status) {
       return (Iterable<Orders>) repositoryOrder.findByStatus(status);
    }

    @Override
    public Iterable<Orders> findByOrderDate(List<LocalDateTime> orderDate) {
        return (Iterable<Orders>) repositoryOrder.findByOrderDate(orderDate);
    }
    
}
