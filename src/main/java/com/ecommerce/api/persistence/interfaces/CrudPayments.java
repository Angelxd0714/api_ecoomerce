package com.ecommerce.api.persistence.interfaces;



import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.dto.request.PaymentRequest;
import com.ecommerce.api.dto.response.PaymentsDTO;
import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.entities.Payments;

import java.time.LocalDate;
import java.util.List;

public interface CrudPayments {
    void createPayment(PaymentRequest payment);
    List<PaymentsDTO> findAll();
    PaymentsDTO findById(Long id);
    void updatePayment(PaymentRequest payment, Long id);
    List<PaymentsDTO> findByUserId(Long userId);
    List<PaymentsDTO> findByPaymentMethod(String paymentMethod);
    List<PaymentsDTO> findByPaymentStatus(String paymentStatus);
    List<PaymentsDTO> findByPaymentDate(LocalDate paymentDate);
    List<PaymentsDTO> findByOrderId(Long orderId);
    void deletePayment(Long id);
}
