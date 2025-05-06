package com.ecommerce.api.dto.request;

import lombok.*;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MarkersRequest {
    private Long id;
    private String name;

    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
