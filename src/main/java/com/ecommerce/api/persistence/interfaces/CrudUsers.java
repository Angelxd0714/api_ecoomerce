package com.ecommerce.api.persistence.interfaces;


import com.ecommerce.api.persistence.entities.Users;

public interface CrudUsers {
    void saveUser(Users user);
    Users getUserById(Long userId);
    Users getUserByEmail(String email);
    Users getUserIdent(Long userId);
    void updateUser(Users user,Long id);
    void deleteUser(Long id);
    Iterable<Users> getAllUsers();
}
