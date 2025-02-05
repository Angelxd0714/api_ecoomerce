package com.ecommerce.api.persistence.interfaces;

import java.io.IOException;
import java.util.List;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;
import org.springframework.web.multipart.MultipartFile;

public interface CrudProduct {
    void save(ProductRequest product, MultipartFile file) throws IOException;
    Product findById(String id);
    void update(ProductRequest product,String id,MultipartFile file) throws IOException;
    void delete(String id);
    List<ProductDTO> findAll();
    Iterable<Product> findByName(List<String> name);
    Iterable<Product> findByCategory(List<String> category);
    Iterable<Product> findByMarker(List<String> marker);
    Iterable<Product> findByPrice(List<Double> price);
}
