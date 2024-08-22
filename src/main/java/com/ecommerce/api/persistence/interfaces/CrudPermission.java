package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Permissions;

public interface CrudPermission {
    void save(Permissions permission);
    void delete(String id);
    Permissions getOne(String id);
    Iterable<Permissions> getAll();
    void update(Permissions permission, String id);
}
