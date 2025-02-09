package com.ecommerce.api.dto.request;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CarRequest {
    private Long userId;
    private ProductRequest[] productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
