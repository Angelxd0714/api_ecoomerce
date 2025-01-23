package com.ecommerce.api.persistence.interfaces;

import org.springframework.data.mongodb.core.aggregation.DateOperators.DateAdd;

import com.ecommerce.api.persistence.entities.Payments;

import java.time.LocalDate;

public interface CrudPayments {
    void createPayment(Payments payment);
    Iterable<Payments> findAll();
    Payments findById(String id);
    void updatePayment(Payments payment,String id);
    Iterable<Payments> findByUserId(String userId);
    Iterable<Payments> findByPaymentMethod(String paymentMethod);
    Iterable<Payments> findByPaymentStatus(String paymentStatus);
    Iterable<Payments> findByPaymentDate(LocalDate paymentDate);
    Iterable<Payments> findByOrderId(String orderId);
    void deletePayment(String id);
}
