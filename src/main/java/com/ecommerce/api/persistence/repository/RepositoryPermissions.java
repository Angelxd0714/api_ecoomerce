package com.ecommerce.api.persistence.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.ecommerce.api.persistence.entities.Permissions;

@Repository
public interface RepositoryPermissions extends CrudRepository<Permissions,Long> {
 Optional<Permissions> findById(Long id);

void deleteById(Long id);

}
