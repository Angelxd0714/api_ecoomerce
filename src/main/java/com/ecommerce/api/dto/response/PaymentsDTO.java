package com.ecommerce.api.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsDTO {
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
}
