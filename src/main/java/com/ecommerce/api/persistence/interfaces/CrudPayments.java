package com.ecommerce.api.persistence.interfaces;



import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.entities.Payments;

import java.time.LocalDate;
import java.util.List;

public interface CrudPayments {
    void createPayment(Payments payment);
    List<Payments> findAll();
    Payments findById(String id);
    void updatePayment(Payments payment,String id);
    List<Payments> findByUserId(Long userId);
    List<Payments> findByPaymentMethod(String paymentMethod);
    List<Payments> findByPaymentStatus(String paymentStatus);
    List<Payments> findByPaymentDate(LocalDate paymentDate);
    List<Payments> findByOrderId(Long orderId);
    void deletePayment(String id);
}
