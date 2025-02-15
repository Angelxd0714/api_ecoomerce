package com.ecommerce.api.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
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
}
