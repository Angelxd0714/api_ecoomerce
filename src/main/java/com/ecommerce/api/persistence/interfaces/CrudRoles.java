package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Roles;

public interface CrudRoles {
    void save(Roles roles);
    void delete(String id);
    Roles findById(String id);
    Iterable<Roles> findAll();
    void update(Roles roles, String id);
    
}
