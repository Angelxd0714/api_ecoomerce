package com.ecommerce.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String username;
    private Long userId;
    private String email;
    private String fullName;
    private String phone;
    private boolean isEnabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private String address;
    private boolean credentialNoExpired;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Set<RolRequest> roles = new HashSet<>();
}
