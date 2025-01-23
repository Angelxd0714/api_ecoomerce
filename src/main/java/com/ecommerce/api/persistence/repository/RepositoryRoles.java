package com.ecommerce.api.persistence.repository;

import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Roles;

@Repository
public interface RepositoryRoles  extends CrudRepository<Roles,String> {
    @Query("select r from Roles r where r.name in rolesName")
    Set<Roles> findRolesByName(List<String> rolesName);
    Optional<Roles> findById(String id);
    void deleteById(String id);
    
}
