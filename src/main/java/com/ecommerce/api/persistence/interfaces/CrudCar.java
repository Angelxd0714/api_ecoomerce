package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Car;
import com.ecommerce.api.persistence.entities.Users;

public interface CrudCar {
    void save(Car car);
    Iterable<Car> findAll();
    Car findById(String id);
    void deleteById(String id);
    Iterable<Car> findByUserId(Users userId);
}
