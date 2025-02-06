package com.ecommerce.api.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String image;
    private List<CategoryDTO> categories; // DTO para Category
    private MarkersDTO marker; // DTO para Markers
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
