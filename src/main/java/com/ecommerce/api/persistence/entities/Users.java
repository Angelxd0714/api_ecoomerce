package com.ecommerce.api.persistence.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class Users {
    @Id
    private String id; // MongoDB utiliza String para el identificador
    @Field(name = "username")
    private String username;
    @Field(name = "user_id")
    @Indexed(unique = true)
    private String userId;
    @Field(name = "password")
    private String password;
    @Indexed(unique = true)
    @Field(name = "email")
    private String email;
    @Field(name = "full_name")
    private String fullName;
    @Field(name = "phone")
    private String phone;
    @Field(name = "is_enabled")
    private boolean isEnabled;
    @Field(name = "account_no_expired")
    private boolean accountNoExpired;
    @Field(name = "account_no_locked")
    private boolean accountNoLocked;
    @Field(name = "address")
    private String address;
    @Field(name = "credential_no_expired")
    private boolean credentialNoExpired;
    @Field(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @Field(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @Field(name = "roles")
    private Set<Roles> roles = new HashSet<>();

}
