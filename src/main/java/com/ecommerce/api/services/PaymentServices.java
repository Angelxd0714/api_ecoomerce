package com.ecommerce.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DateAdd;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Payments;
import com.ecommerce.api.persistence.interfaces.CrudPayments;
import com.ecommerce.api.persistence.repository.RepositoryPayment;

@Service
public class PaymentServices implements CrudPayments {
    @Autowired
    private RepositoryPayment repositoryPayment;

    @Override
    public void createPayment(Payments payment) {
        repositoryPayment.save(payment);
    }

    @Override
    public Iterable<Payments> findAll() {
        return repositoryPayment.findAll();
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
    public Iterable<Payments> findByUserId(String userId) {
        return repositoryPayment.findByUserId(userId);
    }

    @Override
    public Iterable<Payments> findByPaymentMethod(String paymentMethod) {
        return (Iterable<Payments>) repositoryPayment.findByPaymentMethod(paymentMethod);
    }

    @Override
    public Iterable<Payments> findByPaymentStatus(String paymentStatus) {
        return (Iterable<Payments>) repositoryPayment.findByPaymentStatus(paymentStatus);
    }

    @Override
    public Iterable<Payments> findByPaymentDate(DateAdd paymentDate) {
        return (Iterable<Payments>) repositoryPayment.findByPaymentDate(paymentDate);
    }

    @Override
    public Iterable<Payments> findByOrderId(String orderId) {
        return (Iterable<Payments>) repositoryPayment.findByOrderId(orderId);
    }

    @Override
    public void deletePayment(String id) {
        repositoryPayment.deleteById(id);
    }

}
