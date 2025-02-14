package com.ecommerce.api.controllers;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.dto.response.OrdersDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.ecommerce.api.persistence.entities.Orders;

import com.ecommerce.api.services.OrdersServices;
import java.time.*;
import java.util.HashMap;

@CrossOrigin("*")
@RestController
@RequestMapping("api/order")
public class ControllerOrder {
    @Autowired
    private OrdersServices serviceOrder;
    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> getOrders(){
        Map<String,Object> response = new HashMap<>();
        try {
            List<OrdersDTO> orders = serviceOrder.findAll();
            response.put("orders", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/orderGetOne/{id}")
    public ResponseEntity<Map<String,Object>> getOrderById(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        try {
            OrdersDTO order = serviceOrder.findById(id);
            response.put("order", order);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String,Object>> getOrderByUserId(@PathVariable Long userId){
        Map<String,Object> response = new HashMap<>();
        try {
            List<OrdersDTO> orders = serviceOrder.findByUserId(userId);
            response.put("orders", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String,Object>> getOrderByStatus(@PathVariable  String status){
            Map<String,Object> response = new HashMap<>();
        try {
            List<OrdersDTO> orders = serviceOrder.findByStatus(status);
            response.put("orders", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }
    @GetMapping("/date/{date}")
    public ResponseEntity<Map<String,Object>> getOrderByDate(@PathVariable
                                                                 @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date){
            Map<String,Object> response = new HashMap<>();
        try {
            List<OrdersDTO> orders = serviceOrder.findByOrderDate(date);
            response.put("orders", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,String>> updateOrder(@PathVariable String id,@RequestBody OrdersRequest orders){
        Map<String,String> response = new HashMap<>();
        try {
            serviceOrder.update(orders, Long.parseLong(id));
            response.put("message", "Pedido actualizado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,String>> deleteOrder(@PathVariable Long id){
        Map<String,String> response = new HashMap<>();
        try {
            serviceOrder.delete(id);
            response.put("message", "Pedido eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createOrder(@RequestBody OrdersRequest orders){
         Map<String,String> response = new HashMap<>();

        try {
            serviceOrder.save(orders);
            response.put("message", "Pedido creado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
