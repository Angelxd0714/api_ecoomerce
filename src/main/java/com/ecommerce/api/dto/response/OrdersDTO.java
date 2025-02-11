package com.ecommerce.api.dto.response;

import com.ecommerce.api.dto.request.UserRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
@Builder
@Getter
@Setter
public class OrdersDTO {
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private String status;
    private Set<ProductDTO> productIds;
    private BigDecimal totalAmount;
}
