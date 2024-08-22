package com.ecommerce.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Car;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.persistence.interfaces.CrudCar;
import com.ecommerce.api.persistence.repository.RepositoryCar;

@Service
public class CarServices implements CrudCar {
    @Autowired
    private RepositoryCar repositoryCar;
    @Override
    public void save(Car car) {
       repositoryCar.save(car);
    }

    @Override
    public Iterable<Car> findAll() {
      return repositoryCar.findAll();
    }

    @Override
    public Car findById(String id) {
        return repositoryCar.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        repositoryCar.deleteById(id);
    }

    @Override
    public Iterable<Car> findByUserId(Users userId) {
        return repositoryCar.findAllByUserId(userId);
    }

    public void update(String id, Car car) {
       repositoryCar.findById(id).ifPresentOrElse(x->{
        x.setProductId(car.getProductId());
        x.setUpdatedAt(car.getUpdatedAt());;
        x.setCreatedAt(car.getCreatedAt());
        x.setUserId(car.getUserId());
        repositoryCar.save(x);
       }, null);
    }
    
}
