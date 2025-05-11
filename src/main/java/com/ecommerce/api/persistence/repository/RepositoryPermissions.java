package com.ecommerce.api.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.ecommerce.api.dto.request.PermissionsRequest;
import com.ecommerce.api.persistence.entities.Permissions;

@Repository
public interface RepositoryPermissions extends CrudRepository<Permissions, Long> {
    Optional<Permissions> findById(PermissionsRequest permission);

    List<Permissions> findAll();

    void deleteById(Long id);

    Optional<Permissions> findByName(String name);

    List<Permissions> findAllById(Iterable<Long> ids);

}
