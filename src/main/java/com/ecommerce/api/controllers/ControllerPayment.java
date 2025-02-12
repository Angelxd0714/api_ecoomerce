package com.ecommerce.api.controllers;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.dto.request.PaymentRequest;
import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.request.UserRequest;
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
import java.util.HashMap;

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
    public ResponseEntity<Map<String, String>> createPayment(@RequestBody Order orders, @RequestParam("userId") UserRequest user,
                                                             @RequestParam("orders") OrdersRequest order) {
        if (Objects.equals(orders.getStatus(), "CANCELED")) {
            return  ResponseEntity.badRequest().body(Collections.singletonMap("error", "Payment canceled"));
        }
        try {
            PaymentIntent paymentIntent = serviceStripe.createPaymentIntent(orders);
      ;
            OrdersRequest  response = new OrdersRequest();
            response.setOrderDate(LocalDate.ofEpochDay(orders.getCreated()));
            response.setStatus(orders.getStatus());
            response.setProductRequest((Set<ProductRequest>) orders.getProductObject());
            response.setTotalAmount(Double.valueOf(orders.getAmountTotal()));




            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setPaymentAmount(orders.getAmountTotal());
            paymentRequest.setPaymentCurrency(paymentIntent.getCurrency());
            paymentRequest.setPaymentStatus(paymentIntent.getStatus());
            paymentRequest.setCreatedAt(LocalDate.now());
            paymentRequest.setPaymentMethod(paymentIntent.getPaymentMethodTypes().get(0));
            paymentRequest.setUserId(user);
            paymentRequest.setOrderId(order);

            paymentServices.createPayment(paymentRequest);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("clientSecret", paymentIntent.getClientSecret());
            responseMap.put("paymentIntentId", paymentIntent.getId());
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear el pago");
            errorResponse.put("detalle", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);

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
    public ResponseEntity<Map<String,Object>> getPaymentsByUser(@PathVariable Long userId) {
        Map<String,Object> response = new HashMap<>(Map.of("message", "Listado de pagos exitoso."));
        try {
            response.put("payments", paymentServices.findByUserId(userId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los pagos", "detalle", e.getMessage()));
        }
    }
    @GetMapping("/get-payment-by-order/{orderId}")
    public ResponseEntity<?> getPaymentsByOrder(@PathVariable Long orderId) {
        Map<String,Object> response = new HashMap<>(Map.of("message", "Listado de pagos exitoso."));
        try {
            response.put("payments", paymentServices.findByOrderId(orderId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los pagos", "detalle", e.getMessage()));
        }
    }
    @GetMapping("/get-payment-by-id/{id}")
    public ResponseEntity<Map<String,Object>> getPaymentsById(@PathVariable Long id) {
        Map<String,Object> response = new HashMap<>(Map.of("message", "Listado de pagos exitoso."));
        try {
            response.put("payments", paymentServices.findById(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los pagos", "detalle", e.getMessage()));
        }
    }
    @PutMapping("/update-payment/{id}")
    public ResponseEntity<Map<String,String>> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest payments) {
         Map<String,String> response = new HashMap<>(Map.of("message", "Pago actualizado exitosamente."));

        try {
            paymentServices.updatePayment(payments, id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al actualizar el pago", "detalle", e.getMessage()));
        }
    }
    @DeleteMapping("/delete-payment/{id}")
    public ResponseEntity<Map<String,String>> deletePayment(@PathVariable Long id) {
        Map<String,String> response = new HashMap<>(Map.of("message", "Pago eliminado exitosamente."));
        try {
            paymentServices.deletePayment(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al eliminar el pago", "detalle", e.getMessage()));
        }
    }
}
