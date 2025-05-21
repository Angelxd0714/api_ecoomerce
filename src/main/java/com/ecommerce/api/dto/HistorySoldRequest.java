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
public class HistorySoldRequest {
    private Long sellerId;
    private Long productId;
    private BigDecimal quantitySold;
    private String status;
    private Long buyerId;
}
