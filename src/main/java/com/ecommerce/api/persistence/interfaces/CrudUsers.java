package com.ecommerce.api.persistence.interfaces;


import com.ecommerce.api.dto.request.UserRequest;
import com.ecommerce.api.dto.response.UsersDTO;
import com.ecommerce.api.persistence.entities.Users;

import java.util.List;

public interface CrudUsers {

    UsersDTO getUserById(Long userId);
    UsersDTO getUserByEmail(String email);
    UsersDTO getUserIdent(Long userId);
    void updateUser(UserRequest user,Long id);
    void deleteUser(Long id);
    List<UsersDTO> getAllUsers();
}
