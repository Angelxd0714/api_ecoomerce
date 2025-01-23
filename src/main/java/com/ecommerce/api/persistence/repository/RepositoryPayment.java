package com.ecommerce.api.persistence.repository;

import org.springframework.data.mongodb.core.aggregation.DateOperators.DateAdd;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.entities.Payments;

import java.time.LocalDate;

@Repository
public interface RepositoryPayment extends CrudRepository<Payments,String> {
    Payments findByPaymentMethod(String paymentMethod);
    Payments findByPaymentStatus(String status);
    Payments findByTransactionId(String transactionId);
    Payments findByOrderId(String orderId);
    Payments findByPaymentDate(LocalDate paymenDateAdd);
    Iterable<Payments> findByUserId(String userId);

}
