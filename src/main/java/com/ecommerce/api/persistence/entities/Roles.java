package com.ecommerce.api.persistence.entities;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.HashSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="roles")
public class Roles {
    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "permissions")
    private Set<Permissions> permissions = new HashSet<Permissions>();
    
}
