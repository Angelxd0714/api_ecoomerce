package com.ecommerce.api.persistence.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Users;

@Repository
public interface RepositoryUsers extends MongoRepository<Users,String> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUserId(Long userId);
    Optional<Users> findById(String id);
    void deleteById(String id);
}
