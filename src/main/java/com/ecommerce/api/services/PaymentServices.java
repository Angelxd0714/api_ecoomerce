package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Payments;
import com.ecommerce.api.persistence.interfaces.CrudPayments;
import com.ecommerce.api.persistence.repository.RepositoryPayment;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PaymentServices implements CrudPayments {
    @Autowired
    private RepositoryPayment repositoryPayment;

    @Override
    public void createPayment(Payments payment) {
        repositoryPayment.save(payment);
    }

    @Override
    public List<Payments> findAll() {
        return (List<Payments>) repositoryPayment.findAll();
    }

    @Override
    public Payments findById(String id) {
        return repositoryPayment.findById(id).orElse(null);
    }

    @Override
    public void updatePayment(Payments payment, String id) {
        repositoryPayment.findById(id).ifPresent(existingPayment -> {
            existingPayment.setUserId(payment.getUserId());
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            existingPayment.setPaymentAmount(payment.getPaymentAmount());
            existingPayment.setPaymentStatus(payment.getPaymentStatus());
            existingPayment.setPaymentCurrency(payment.getPaymentCurrency());
            existingPayment.setPaymentDate(payment.getPaymentDate());
            existingPayment.setTransactionId(payment.getTransactionId());
            repositoryPayment.save(existingPayment);
        });
    }

    @Override
    public List<Payments> findByUserId(Long userId) {
               Users user = Users.builder()
                .id(userId)
                .build();


        return (List<Payments>) repositoryPayment.findByUserId(user);
    }

    @Override
    public List<Payments> findByPaymentMethod(String paymentMethod) {
        return (List<Payments>) repositoryPayment.findByPaymentMethod(paymentMethod);
    }

    @Override
    public List<Payments> findByPaymentStatus(String paymentStatus) {
        return (List<Payments>) repositoryPayment.findByPaymentStatus(paymentStatus);
    }

    @Override
    public List<Payments> findByPaymentDate(LocalDate paymentDate) {
        return (List<Payments>) repositoryPayment.findByPaymentDate(paymentDate);
    }

    @Override
    public List<Payments> findByOrderId(Long orderId) {
        Orders orders = Orders.builder()
                .id(orderId)
                .build();
        return (List<Payments>) repositoryPayment.findByOrderId(orders);
    }

    @Override
    public void deletePayment(String id) {
        repositoryPayment.deleteById(id);
    }

}
