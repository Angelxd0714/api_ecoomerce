package com.ecommerce.api.dto.request;

import lombok.*;

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
    private Double totalAmount;
   private Set<ProductRequest> productRequest;

}
