package com.ecommerce.api.persistence.repository;

import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Payments;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositoryPayment extends CrudRepository<Payments,Long> {
    Payments findByPaymentMethod(String paymentMethod);
    Payments findByPaymentStatus(String status);
    Payments findByTransactionId(String transactionId);
    Payments findByOrderId(Orders orderId);
    Payments findByPaymentDate(LocalDate paymenDateAdd);
    List<Payments> findByUserId(Users userId);
    List<Payments> findAll();


}
