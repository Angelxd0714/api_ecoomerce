package com.ecommerce.api.persistence.repository;

import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Roles;

@Repository
public interface RepositoryRoles  extends MongoRepository<Roles,String>{
    @Query("{ 'name': { $in: ?0 } }")
    Set<Roles> findRolesByName(List<String> rolesName);
    Optional<Roles> findById(String id);
    void deleteById(String id);
    
}
