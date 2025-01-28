package com.ecommerce.api.controllers;

import com.stripe.model.climate.Order;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.Collections;

import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.entities.Payments;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.services.PaymentServices;
import com.ecommerce.api.services.ServiceStripe;
import com.stripe.model.PaymentIntent;

@CrossOrigin("*")
@RestController
@RequestMapping("api/payment")
public class ControllerPayment {
    @Autowired
    private PaymentServices paymentServices;
    @Autowired
    private ServiceStripe serviceStripe;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPayment(@RequestBody Order orders, @RequestParam("userId") Users user,
                                                             @RequestParam("orders") Orders order) {
        if (Objects.equals(orders.getStatus(), "CANCELED")) {
            return  ResponseEntity.badRequest().body(Collections.singletonMap("error", "Payment canceled"));
        }
        try {
            PaymentIntent paymentIntent = serviceStripe.createPaymentIntent(orders);
            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());
            response.put("id", paymentIntent.getId());
            response.put("status", paymentIntent.getStatus());
            response.put("amount", String.valueOf(paymentIntent.getAmount()));
            response.put("currency", paymentIntent.getCurrency());
            response.put("description", paymentIntent.getDescription());
            Payments payments = new Payments();
            payments.setPaymentAmount(orders.getAmountTotal());
            payments.setId(Long.valueOf(paymentIntent.getId()));
            payments.setPaymentCurrency(paymentIntent.getCurrency());

            payments.setCreatedAt(LocalDate.now());
            payments.setPaymentStatus(paymentIntent.getStatus());
            payments.setUserId(user);
            payments.setOrderId(order);
            paymentServices.createPayment(payments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }

    }
    @GetMapping("/get-payment")
    public ResponseEntity<Map<String,String>> getPayments() {
        try {
            return ResponseEntity.ok(Collections.singletonMap("payments", String.valueOf(paymentServices.findAll())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    @GetMapping("/get-payment-by-user/{userId}")
    public ResponseEntity<?> getPaymentsByUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(paymentServices.findByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/get-payment-by-order/{orderId}")
    public ResponseEntity<?> getPaymentsByOrder(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok(paymentServices.findByOrderId(orderId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/get-payment-by-id/{id}")
    public ResponseEntity<?> getPaymentsById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(paymentServices.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/update-payment/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable String id, @RequestBody Payments payments) {
        try {
            paymentServices.updatePayment(payments, id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete-payment/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable String id) {
        try {
            paymentServices.deletePayment(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
