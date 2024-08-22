package com.ecommerce.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Roles;
import com.ecommerce.api.persistence.interfaces.CrudRoles;
import com.ecommerce.api.persistence.repository.RepositoryRoles;

@Service
public class RolesServices implements CrudRoles{
    @Autowired
    private RepositoryRoles repositoryRoles;

    @Override
    public void save(Roles roles) {
        repositoryRoles.save(roles);
    }

    @Override
    public void delete(String id) {
       repositoryRoles.deleteById(id);
    }

    @Override
    public Roles findById(String id) {
       return repositoryRoles.findById(id).orElse(null);
    }

    @Override
    public Iterable<Roles> findAll() {
       return repositoryRoles.findAll();
    }

    @Override
    public void update(Roles roles, String id) {
        repositoryRoles.findById(id).ifPresent(r->{
            r.setName(roles.getName());
            repositoryRoles.save(r);
        });
    }
    
}
