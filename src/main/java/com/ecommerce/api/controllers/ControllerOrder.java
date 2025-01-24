package com.ecommerce.api.controllers;

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
import com.ecommerce.api.persistence.entities.Order;

import com.ecommerce.api.services.OrdersServices;
import java.time.*;
@CrossOrigin("*")
@RestController
@RequestMapping("api/order")
public class ControllerOrder {
    @Autowired
    private OrdersServices serviceOrder;
    @GetMapping("/all")
    public ResponseEntity<?> getOrders(){
        try {
            return ResponseEntity.ok(serviceOrder.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/orderGetOne/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id){
        try {
            return ResponseEntity.ok(serviceOrder.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrderByUserId(@PathVariable List<String> userId){
        try {
            return ResponseEntity.ok(serviceOrder.findByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrderByStatus(@PathVariable  List<String> status){
        try {
            return ResponseEntity.ok(serviceOrder.findByStatus(status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/date/{date}")
    public ResponseEntity<?> getOrderByDate(@PathVariable  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") List<LocalDateTime> date){
        try {
            return ResponseEntity.ok(serviceOrder.findByOrderDate(date));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable String id,@RequestBody Order orders){
        try {
            serviceOrder.update(orders, Long.valueOf(id));
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id){
        try {
            serviceOrder.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Order orders){
        try {
            serviceOrder.save(orders);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
