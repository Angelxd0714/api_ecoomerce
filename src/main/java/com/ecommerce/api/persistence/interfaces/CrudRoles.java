package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.dto.request.RolRequest;
import com.ecommerce.api.dto.response.RolesDTO;
import com.ecommerce.api.persistence.entities.Roles;

import java.util.List;

public interface CrudRoles {
    void save(RolRequest roles);
    void delete(Long id);
    RolesDTO findById(Long id);
    List<RolesDTO> findAll();
    void update(RolRequest roles, Long id);
    
}
