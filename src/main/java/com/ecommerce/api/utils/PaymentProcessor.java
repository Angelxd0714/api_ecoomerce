package com.ecommerce.api.utils;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.dto.request.PaymentRequest;
import com.ecommerce.api.dto.response.UsersDTO;
import com.ecommerce.api.services.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentProcessor {
    @Autowired
    private StripePago serviceMercadoPago;

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
        if (paymentRequest == null) {
            System.err.println("❌ Error: Mensaje recibido como null");
            return;
        }

        System.out.println("✅ Mensaje recibido correctamente: " + paymentRequest.getPaymentStatus());
        UsersDTO user = userDetailService.getUserIdent(paymentRequest.getUserId());

        try {
            Pair<Boolean, Long> result = serviceMercadoPago.processPayment(
                    paymentRequest, user.getEmail(), paymentRequest.getPaymentAmount());

            if (result.getLeft()) {
                OrdersRequest order = OrdersRequest.builder()
                        .userId(user.getUserId())
                        .orderDate(paymentRequest.getPaymentDate())
                        .status("Pagado")
                        .totalAmount(paymentRequest.getPaymentAmount())
                        .build();
                ordersServices.save(order);
                productServices.updateProduct(paymentRequest.getOrderId());
            } else {
                OrdersRequest order = OrdersRequest.builder()
                        .userId(user.getUserId())
                        .orderDate(paymentRequest.getPaymentDate())
                        .status("Rechazado")
                        .totalAmount(paymentRequest.getPaymentAmount())
                        .build();
                ordersServices.save(order);
            }

        } catch (AmqpRejectAndDontRequeueException e) {
            System.err.println("❌ Token inválido, descartando mensaje de la cola...");
            log.error("❌ Token inválido, descartando mensaje de la cola...", e);
            throw new AmqpRejectAndDontRequeueException("Invalid token, message removed");
        }
    }
}
