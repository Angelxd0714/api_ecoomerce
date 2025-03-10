package com.ecommerce.api.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequest {
    private Long id;
    private Long orderId;
    private Long userId;
    private String paymentMethod;
    private BigDecimal paymentAmount;
    private String paymentStatus;
    private String paymentCurrency;
    private LocalDate paymentDate;
    private String transactionId;
    private LocalDate createdAt;
    private String mpCardToken;  // Solo para tarjetas
}
