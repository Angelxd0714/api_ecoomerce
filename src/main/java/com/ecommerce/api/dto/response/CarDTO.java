package com.ecommerce.api.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CarDTO {
    private Long userId;
    private List<ProductDTO> productId = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
