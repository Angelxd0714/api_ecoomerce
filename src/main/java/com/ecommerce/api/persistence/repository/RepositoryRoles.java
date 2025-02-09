package com.ecommerce.api.persistence.repository;

import java.util.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Roles;

@Repository
public interface RepositoryRoles  extends CrudRepository<Roles,Long> {
    @Query("select r from Roles r where r.name in :rolesName")
    Set<Roles> findRolesByName(@Param("rolesName") List<String> rolesName);
    Optional<Roles> findById(Long id);
    void deleteById(Long id);
    
}
