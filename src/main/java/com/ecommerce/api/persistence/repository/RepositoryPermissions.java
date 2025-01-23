package com.ecommerce.api.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.ecommerce.api.persistence.entities.Permissions;

@Repository
public interface RepositoryPermissions extends CrudRepository<Permissions,String> {
 Optional<Permissions> findById(String id);

void deleteById(String id);

}
