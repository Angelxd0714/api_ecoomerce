package com.ecommerce.api.dto.request;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequest {
    private Long id;
    private OrdersRequest orderId;
    private UserRequest userId;
    private String paymentMethod;
    private Long paymentAmount;
    private String paymentStatus;
    private String paymentCurrency;
    private LocalDate paymentDate;
    private String transactionId;
    private LocalDate createdAt;
}
