package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.PermissionsRequest;
import com.ecommerce.api.dto.response.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Permissions;
import com.ecommerce.api.persistence.interfaces.CrudPermission;
import com.ecommerce.api.persistence.repository.RepositoryPermissions;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class PermissionServices implements CrudPermission {
    @Autowired
    private RepositoryPermissions repositoryPermissions;

    @Transactional
    @Override
    public void save(PermissionsRequest permission) {
        Permissions permissions = Permissions.builder()
                .name(permission.getName())
                .build();

        repositoryPermissions.save(permissions);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repositoryPermissions.deleteById(id);
    }

    @Override
    public PermissionDTO getOne(Long id) {

        PermissionDTO permissionDTO = new PermissionDTO();
        Permissions permissions = repositoryPermissions.findById(id).orElse(null);
        permissionDTO.setId(permissions.getId());
        permissionDTO.setName(permissions.getName());
        return permissionDTO;

    }

    @Override
    public List<PermissionDTO> getAll() {

        return repositoryPermissions.findAll().stream().map(permission -> {
            PermissionDTO permissionDTO = new PermissionDTO();
            permissionDTO.setId(permission.getId());
            permissionDTO.setName(permission.getName());
            return permissionDTO;
        }).toList();
    }

    @Transactional
    @Override
    public void update(PermissionsRequest permission, Long id) {
        repositoryPermissions.findById(id).ifPresent(p -> {
            p.setName(permission.getName());
            p.setName(permission.getName());
            repositoryPermissions.save(p);
        });
    }

}
