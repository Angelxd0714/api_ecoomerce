package com.ecommerce.api.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Car;
import com.ecommerce.api.persistence.entities.Users;

@Repository
public interface RepositoryCar extends MongoRepository<Car,String> {

    Iterable<Car> findAllByUserId(Users userId);
    
}
