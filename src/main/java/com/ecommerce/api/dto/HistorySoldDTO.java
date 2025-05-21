package com.ecommerce.api.dto;

import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.dto.response.UsersDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistorySoldDTO {
    private Long id;
    private UsersDTO seller;
    private ProductDTO product;
    private BigDecimal quantitySold;
    private BigDecimal totalSold;
    private String status;
    private LocalDate soldDate;
    private UsersDTO buyer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
