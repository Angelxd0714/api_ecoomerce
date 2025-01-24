package com.ecommerce.api.persistence.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Car;
import com.ecommerce.api.persistence.entities.Users;

@Repository
public interface RepositoryCar extends CrudRepository<Car,String> {

    Iterable<Car> findAllByUserId(Users userId);
    
}
