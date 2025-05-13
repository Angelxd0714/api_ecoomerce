package com.ecommerce.api.cart;

import org.junit.Test;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.ecommerce.api.dto.request.CarRequest;
import com.ecommerce.api.dto.response.CarDTO;
import com.ecommerce.api.services.CarServices;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CarServicesTest {

    private CarServices carServices;
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test_add_to_cart_stores_items_in_redis_with_correct_key_and_expiration() {
        // Arrange
        CarRequest productData = new CarRequest();
        Long userId = 123L;
        Long[] productIds = { 1L, 2L };
        productData.setUserId(userId);
        productData.setProductId(productIds);

        redisTemplate = mock(RedisTemplate.class);
        ListOperations<String, Object> listOperations = mock(ListOperations.class);

        when(redisTemplate.opsForList()).thenReturn(listOperations);

        carServices = new CarServices(redisTemplate);

        carServices.addToCart(productData);

        // Assert
        String expectedKey = "cart:" + userId;
        verify(listOperations).rightPush(eq(expectedKey), eq(productData));
        verify(redisTemplate).expire(eq(expectedKey), eq(1L), eq(TimeUnit.HOURS));
    }

    // Getting cart for non-existent or empty cart returns empty list
    @Test
    public void test_get_cart_returns_empty_list_for_nonexistent_cart() {
        // Arrange
        Long userId = 123L;
        String key = "cart:" + userId;

        redisTemplate = mock(RedisTemplate.class);
        ListOperations<String, Object> listOperations = mock(ListOperations.class);

        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.range(eq(key), eq(0L), eq(-1L))).thenReturn(null);

        carServices = new CarServices(redisTemplate);

        List<CarDTO> result = carServices.getCart(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        when(listOperations.range(eq(key), eq(0L), eq(-1L))).thenReturn(Collections.emptyList());

        result = carServices.getCart(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}