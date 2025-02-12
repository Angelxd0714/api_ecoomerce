package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.request.UserRequest;
import com.ecommerce.api.dto.response.OrdersDTO;
import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.persistence.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.interfaces.CrudOrder;
import com.ecommerce.api.persistence.repository.RepositoryOrder;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdersServices implements CrudOrder {
    @Autowired
    private RepositoryOrder repositoryOrder;


    @Override
    public void save(OrdersRequest order) {
        Orders orders = Orders.builder()
                .user(Users.builder().id(order.getUserId().getId()).build())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                        .products(order.getProductRequest().stream().map(product -> com.ecommerce.api.persistence.entities.Product.builder()
                                .id(Long.parseLong(product.getId()))
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .build()).collect(Collectors.toSet()))
                .totalAmount(BigDecimal.valueOf(order.getTotalAmount()))
                .build();

        repositoryOrder.save(orders);
    }

    @Override
    public OrdersDTO findById(Long id) {

        OrdersDTO ordersDTO = repositoryOrder.findById(id).map(order -> OrdersDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getUserId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .productIds(order.getProducts().stream().map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build()).collect(Collectors.toSet())
                        )
                .totalAmount(order.getTotalAmount())
                .build()).orElse(null);

        if (ordersDTO == null) {
            throw new IllegalArgumentException("El pedido no existe");
        }
        return ordersDTO;
    }

    @Override
    public void update(OrdersRequest orders, Long id) {
       repositoryOrder.updateOrder(id, orders.getStatus());
    }

    @Override
    public void delete(Long id) {
        repositoryOrder.deleteById(id);
    }

    @Override
    public List<OrdersDTO> findAll() {


        return repositoryOrder.findAll().stream().map(order ->
                OrdersDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getUserId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .productIds(order.getProducts().stream().map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build()).collect(Collectors.toSet())
                )
                .totalAmount(order.getTotalAmount())
                .build()).toList();
    }

    @Override
    public List<OrdersDTO> findByUserId( Long userId) {

        List<OrdersDTO> ordersDTOS = repositoryOrder.findByUserId(userId).stream().map(order -> OrdersDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getUserId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .productIds(order.getProducts().stream().map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build()).collect(Collectors.toSet())
                )
                .totalAmount(order.getTotalAmount())
                .build()).toList();
        if (ordersDTOS.isEmpty()) {
            throw new IllegalArgumentException("El pedido no existe");
        }
        return ordersDTOS;
    }

    @Override
    public List<OrdersDTO> findByStatus(String status) {

        List<OrdersDTO> ordersDTOS = repositoryOrder.findByStatus(status).stream().map(order -> OrdersDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getUserId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .productIds(order.getProducts()
                        .stream().map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build()).collect(Collectors.toSet())
                )
                .totalAmount(order.getTotalAmount())
                .build()).toList();
        if (ordersDTOS.isEmpty()) {
            throw new IllegalArgumentException("El pedido no existe");
        }

        return ordersDTOS;
    }

    @Override
    public List<OrdersDTO> findByOrderDate(List<LocalDateTime> orderDate) {
         return repositoryOrder.findByOrderDate(orderDate.get(0)).stream().map(order -> OrdersDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getUserId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .productIds(order.getProducts().stream().map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build()).collect(Collectors.toSet())
                )
                .totalAmount(order.getTotalAmount())
                .build()).toList();


        }
    }
    

