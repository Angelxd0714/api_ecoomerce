package com.ecommerce.api.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdersRequest {
    private Long id;
    private Long userId;
    private LocalDate  orderDate;
   private String status;
    private BigDecimal totalAmount;
   private Set<Long> productRequest;

}
