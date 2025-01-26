package com.ecommerce.api.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Users;

@Repository
public interface RepositoryUsers extends CrudRepository<Users,Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUserId(Long userId);
    Optional<Users> findById(Long id);
    void deleteById(Long id);
}
