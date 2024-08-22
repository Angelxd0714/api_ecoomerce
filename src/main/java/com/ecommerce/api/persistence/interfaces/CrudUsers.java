package com.ecommerce.api.persistence.interfaces;


import com.ecommerce.api.persistence.entities.Users;

public interface CrudUsers {
    void saveUser(Users user);
    Users getUserById(String id);
    Users getUserByEmail(String email);
    Users getUserIdent(Long userId);
    void updateUser(Users user,String id);
    void deleteUser(String id);
    Iterable<Users> getAllUsers();
}
