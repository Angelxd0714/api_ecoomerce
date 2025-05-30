package com.ecommerce.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryBoughtRequest {
    private Long userId;
    private Long productId;
    private BigDecimal price;
    private int quantity;
    private Long paymentMethodId;
    private String status;
    private String deliveryAddress;
}
