package com.ecommerce.api.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Permissions;
import com.ecommerce.api.persistence.interfaces.CrudPermission;
import com.ecommerce.api.persistence.repository.RepositoryPermissions;

@Service
public class PermissionServices implements CrudPermission {
    @Autowired
    private RepositoryPermissions repositoryPermissions;

    @Override
    public void save(Permissions permission) {
        repositoryPermissions.save(permission);
    }

    @Override
    public void delete(String id) {
        repositoryPermissions.deleteById(id);
    }

    @Override
    public Permissions getOne(String id) {
       return repositoryPermissions.findById(id).orElse(null);
    }

    @Override
    public Iterable<Permissions> getAll() {
       return repositoryPermissions.findAll();
    }

    @Override
    public void update(Permissions permission, String id) {
         repositoryPermissions.findById(id).ifPresent(p -> {
            p.setName(permission.getName());
            p.setName(permission.getName());
            repositoryPermissions.save(p);
        });
    }
    
}
