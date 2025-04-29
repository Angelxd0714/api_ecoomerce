package com.ecommerce.api.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CarRequest {
    private Long userId;
    private Long[] productId;
    private Integer quantity;
    private String createdAt;
    private String updatedAt;

}
