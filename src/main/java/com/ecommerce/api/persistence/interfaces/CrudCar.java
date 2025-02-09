package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.dto.request.CarRequest;
import com.ecommerce.api.dto.response.CarDTO;
import com.ecommerce.api.persistence.entities.Car;
import com.ecommerce.api.persistence.entities.Users;

import java.util.List;

public interface CrudCar {
    void save(CarRequest car);
    List<CarDTO> findAll();
    CarDTO findById(Long id);
    void deleteById(Long id);
    List<CarDTO> findByUserId(Users userId);
}
