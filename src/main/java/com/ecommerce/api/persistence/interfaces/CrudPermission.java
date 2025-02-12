package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.dto.request.PermissionsRequest;
import com.ecommerce.api.dto.response.PermissionDTO;
import com.ecommerce.api.persistence.entities.Permissions;

import java.util.List;

public interface CrudPermission {
    void save(PermissionsRequest permission);
    void delete(Long id);
    PermissionDTO getOne(Long id);
    List<PermissionDTO> getAll();
    void update(PermissionsRequest permission, Long id);
}
