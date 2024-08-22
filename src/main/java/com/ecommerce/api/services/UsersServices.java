package com.ecommerce.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.persistence.interfaces.CrudUsers;
import com.ecommerce.api.persistence.repository.RepositoryUsers;

@Service
public class UsersServices implements CrudUsers {
    @Autowired
    private RepositoryUsers repositoryUsers;

    @Override
    public void saveUser(Users user) {
        repositoryUsers.save(user);
    }

    @Override
    public Users getUserById(String id) {
        return repositoryUsers.findById(id).orElse(null);
    }

    @Override
    public Users getUserByEmail(String email) {
        return repositoryUsers.findByEmail(email).orElse(null);
    }

    @Override
    public Users getUserIdent(Long userId) {
        return repositoryUsers.findByUserId(userId).orElse(null);
    }

    @Override
    public void updateUser(Users user, String id) {
        repositoryUsers.findById(id).ifPresentOrElse(x -> {
            x.setFullName(user.getFullName());
            x.setEmail(user.getEmail());
            x.setPassword(user.getPassword());
            x.setRoles(user.getRoles());
            x.setUserId(user.getUserId());
            x.setCreatedAt(user.getCreatedAt());
            x.setUpdatedAt(user.getUpdatedAt());
            x.setPhone(x.getPhone());
            x.setAddress(user.getAddress());
            repositoryUsers.save(x);
        }, null);
    }

    @Override
    public void deleteUser(String id) {
        repositoryUsers.deleteById(id);
    }

    @Override
    public Iterable<Users> getAllUsers() {
        return repositoryUsers.findAll();
    }

}
