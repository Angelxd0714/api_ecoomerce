package com.ecommerce.api.persistence.repository;


import com.ecommerce.api.persistence.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Car;
import com.ecommerce.api.persistence.entities.Users;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepositoryCar extends CrudRepository<Car,Long> {

    List<Car> findAllByUserId(Users userId);

    List<Car> findAll();
    void updateCarByUserId(Users userId, LocalDateTime updatedAt,Product[] productId);
}
