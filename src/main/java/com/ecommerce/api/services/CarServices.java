package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.CarRequest;
import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.CarDTO;
import com.ecommerce.api.persistence.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Car;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.persistence.interfaces.CrudCar;
import com.ecommerce.api.persistence.repository.RepositoryCar;

import java.util.Arrays;
import java.util.List;

@Service
public class CarServices implements CrudCar {
    @Autowired
    private RepositoryCar repositoryCar;
    @Override
    public void save(CarRequest car) {

        Car car1 = Car.builder()
                .userId(Users.builder().id(car.getUserId()).build())
                .createdAt(car.getCreatedAt())
                .updatedAt(car.getUpdatedAt())
                .build();



        repositoryCar.save(car1);
    }

    @Override
    public List<CarDTO> findAll() {
        return repositoryCar.findAll().stream().map(car -> CarDTO.builder()
                .userId(car.getUserId().getId())
                .productId(
                        Arrays.stream(car.getProductId()).map(product -> ProductRequest.builder()
                                .id(String.valueOf(product.getId()))
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .build()).toArray(ProductRequest[]::new)
                )
                .createdAt(car.getCreatedAt())
                .updatedAt(car.getUpdatedAt())
                .build()).toList();

    }

    @Override
    public CarDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        Car car = repositoryCar.findById(id).orElse(null);
        if (car == null) {
            throw new IllegalArgumentException("El carro no existe");
        }

        return (CarDTO) CarDTO.builder()
                .userId(car.getUserId().getId())
                .productId(Arrays.stream(car.getProductId()).map(product -> ProductRequest.builder()
                        .id(String.valueOf(product.getId()))
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build()).toArray(ProductRequest[]::new)).build();
    }

    @Override
    public void deleteById(Long id) {
        repositoryCar.deleteById(id);
    }

    @Override
    public List<CarDTO> findByUserId(Users userId) {

        if (userId == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        return repositoryCar.findAllByUserId(userId).stream().map(car -> CarDTO.builder()
                 .userId(car.getUserId().getId())
                .productId(
                        Arrays.stream(car.getProductId()).map(product -> ProductRequest.builder()
                                .id(String.valueOf(product.getId()))
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .build()).toArray(ProductRequest[]::new)
                )
                .createdAt(car.getCreatedAt())
                .updatedAt(car.getUpdatedAt())
                .build()).toList();

    }

    public void update(Long id, CarRequest car) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        if (car == null) {
            throw new IllegalArgumentException("El carro no puede ser nulo");
        }
        if (car.getUserId() == null) {
            throw new IllegalArgumentException("El id del usuario no puede ser nulo");
        }
        Product [] products = Arrays.stream(car.getProductId()).map(product -> Product.builder()
                .id(Long.parseLong(product.getId()))
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build()).toArray(Product[]::new);


       repositoryCar.findById(id).ifPresentOrElse(x->{
        x.setProductId(products);
        x.setUpdatedAt(car.getUpdatedAt());;
        x.setCreatedAt(car.getCreatedAt());
        x.setUserId(Users.builder().id(car.getUserId()).build());
        repositoryCar.save(x);
       }, null);
    }
    
}
