package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.CarRequest;
import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.CarDTO;
import com.ecommerce.api.dto.response.CategoryDTO;
import com.ecommerce.api.dto.response.MarkersDTO;
import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.persistence.entities.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Car;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.persistence.interfaces.CrudCar;
import com.ecommerce.api.persistence.repository.RepositoryCar;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServices implements CrudCar {
    @Autowired
    private RepositoryCar repositoryCar;
    @Autowired
    private ObjectMapper objectMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    public void addToCart(CarRequest productData) {
        String key = "cart:" + productData.getUserId();
        redisTemplate.opsForList().rightPush(key, productData);
        redisTemplate.expire(key, 1, TimeUnit.HOURS); // Expira en 1 hora
    }
    public List<CarDTO> getCart(Long userId) {
        String key = "cart:" + userId;
        List<Object> cartItems = redisTemplate.opsForList().range(key, 0, -1);

        if (cartItems == null || cartItems.isEmpty()) {
            return Collections.emptyList();
        }

        return cartItems.stream()
                .map(item -> objectMapper.convertValue(item, CarRequest.class)) // Convertir correctamente
                .map(carRequest -> {
                    List<Long> products = Arrays.stream(carRequest.getProductId()).toList();

                    return CarDTO.builder()
                            .userId(carRequest.getUserId())
                            .productId(products)
                            .createdAt(carRequest.getCreatedAt())
                            .updatedAt(carRequest.getUpdatedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }


    public void clearCart(Long userId) {
        String key = "cart:" + userId;
        redisTemplate.delete(key);
    }
    @Override
    public void save(CarRequest car) {

        Car car1 = Car.builder()
                .userId(Users.builder().id(car.getUserId()).build())
                .quantity(car.getQuantity())
                .productId(Arrays.stream(car.getProductId()).map(x->Product.builder().id(x).build()).toArray(Product[]::new))
                .createdAt(
                        LocalDateTime.now()
                )
                .build();



        repositoryCar.save(car1);
    }

    @Override
    public List<CarDTO> findAll() {



        return
                repositoryCar.findAll().stream().map(car -> CarDTO.builder()
                .userId(car.getUserId().getId())
                .productId(
                        List.of(Arrays.stream(car.getProductId()).map(Product::getId).toArray(Long[]::new))
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

        return  CarDTO.builder()
                .userId(car.getUserId().getId())
                .productId(
                        List.of(Arrays.stream(car.getProductId()).map(Product::getId).toArray(Long[]::new))

                )
                .createdAt(car.getCreatedAt())
                .updatedAt(car.getUpdatedAt())
                .build();
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

        return  repositoryCar.findAllByUserId(userId).stream().map(car -> CarDTO.builder()
                .userId(car.getUserId().getId())
                .productId(
                        List.of(Arrays.stream(car.getProductId()).map(Product::getId).toArray(Long[]::new))

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
