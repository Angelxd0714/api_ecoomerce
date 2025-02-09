package com.ecommerce.api.dto.response;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.request.UserRequest;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CarDTO {
    private Long userId;
    private ProductRequest[] productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
