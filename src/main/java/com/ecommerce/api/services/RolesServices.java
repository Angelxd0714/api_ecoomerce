package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.RolRequest;
import com.ecommerce.api.dto.response.PermissionDTO;
import com.ecommerce.api.dto.response.RolesDTO;
import com.ecommerce.api.persistence.entities.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Roles;
import com.ecommerce.api.persistence.interfaces.CrudRoles;
import com.ecommerce.api.persistence.repository.RepositoryPermissions;
import com.ecommerce.api.persistence.repository.RepositoryRoles;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolesServices implements CrudRoles {
        @Autowired
        private RepositoryRoles repositoryRoles;
        @Autowired
        private RepositoryPermissions repositoryPermissions;

        @Transactional
        @Override
        public void save(RolRequest roles) {
                Set<Permissions> permissions = repositoryPermissions.findAllById(roles.getPermissions()).stream()
                                .collect(Collectors.toSet());

                System.out.println("permissions: " + permissions);
                Roles roles1 = Roles.builder()
                                .name(roles.getName())
                                .permissions(permissions)
                                .build();

                repositoryRoles.save(roles1);
        }

        @Transactional
        @Override
        public void delete(Long id) {
                repositoryRoles.deleteById(id);
        }

        @Override
        public RolesDTO findById(Long id) {

                Set<Permissions> permissions = repositoryRoles.findById(id).get().getPermissions();

                return (RolesDTO) RolesDTO.builder()
                                .id(id)
                                .name(repositoryRoles.findById(id).get().getName())
                                .permissions(permissions.stream().map(Permissions::getName).collect(Collectors.toSet())
                                                .stream().map(permission -> PermissionDTO.builder()
                                                                .name(permission)
                                                                .build())
                                                .collect(Collectors.toSet()))
                                .build();

        }

        @Override
        public List<RolesDTO> findAll() {

                return repositoryRoles.findAll().stream().map(roles -> RolesDTO.builder()
                                .id(roles.getId())
                                .name(roles.getName())
                                .permissions(roles.getPermissions().stream().map(Permissions::getName)
                                                .collect(Collectors.toSet())
                                                .stream().map(permission -> PermissionDTO.builder()
                                                                .name(permission)
                                                                .build())
                                                .collect(Collectors.toSet()))
                                .build()).toList();
        }

        @Transactional
        @Override
        public void update(RolRequest roles, Long id) {
                repositoryRoles.findById(id).ifPresent(r -> {
                        r.setName(roles.getName());
                        repositoryRoles.save(r);
                });
        }

}
