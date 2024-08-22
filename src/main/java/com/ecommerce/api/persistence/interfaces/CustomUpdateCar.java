package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Car;

public interface CustomUpdateCar {
    void updateCar(String id,Car car);
}
