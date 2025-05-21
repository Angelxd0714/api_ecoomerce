package com.ecommerce.api.dto;

import com.ecommerce.api.dto.response.UsersDTO;
import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.dto.response.PaymentsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryBoughtDTO {
    private Long id;
    private UsersDTO user;
    private ProductDTO product;
    private LocalDate purchaseDate;
    private BigDecimal price;
    private int quantity;
    private BigDecimal total;
    private PaymentsDTO paymentMethod;
    private String status;
    private String deliveryAddress;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
