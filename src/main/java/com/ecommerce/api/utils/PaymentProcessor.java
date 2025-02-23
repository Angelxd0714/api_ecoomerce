package com.ecommerce.api.utils;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.dto.request.PaymentRequest;
import com.ecommerce.api.dto.response.UsersDTO;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.services.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component

public class PaymentProcessor {
    @Autowired
    private ServiceMercadoPago serviceMercadoPago;

    @Autowired
    private PaymentServices  paymentServices;

    @Autowired
    private OrdersServices  ordersServices;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private ProductServices productServices;

    @RabbitListener(queues = "payment_queue")
    public void processPayment(PaymentRequest paymentRequest) {
        UsersDTO user = userDetailService.getUserById(paymentRequest.getUserId());
        System.out.println("payment: " + paymentRequest.getPaymentAmount());
        Pair<Boolean, Long> result = serviceMercadoPago.processPayment(paymentRequest, user.getEmail(), BigDecimal.valueOf(paymentRequest.getPaymentAmount()));
        if (result.getLeft()) {
            OrdersRequest order = OrdersRequest.builder()
                    .userId(user.getUserId())
                    .orderDate(paymentRequest.getPaymentDate())
                    .status("Pagado")
                    .totalAmount(BigDecimal.valueOf(paymentRequest.getPaymentAmount()))
                    .build();
            ordersServices.save(order);
            productServices.updateProduct(paymentRequest.getOrderId());
        }else{
             OrdersRequest order = OrdersRequest.builder()
                    .userId(user.getUserId())
                    .orderDate(paymentRequest.getPaymentDate())
                    .status("Rechazado")
                    .totalAmount(BigDecimal.valueOf(paymentRequest.getPaymentAmount()))
                    .build();
            ordersServices.save(order);
        }
    }
}
