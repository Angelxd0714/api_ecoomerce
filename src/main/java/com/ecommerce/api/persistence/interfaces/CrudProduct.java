package com.ecommerce.api.persistence.interfaces;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;
import org.springframework.web.multipart.MultipartFile;

public interface CrudProduct {
    void save(ProductRequest product, MultipartFile file) throws IOException;
    ProductDTO findById(Long id);
    void update(ProductRequest product,Long id,MultipartFile file) throws IOException;
    void delete(Long id);
    List<ProductDTO> findAll();
    List<ProductDTO> findByName(String name);
    List<ProductDTO> findByCategory(String category);
    List<ProductDTO> findByMarker(String marker);
    List<ProductDTO> findByPrice(BigDecimal price);
    List<ProductDTO> findByIds(List<Long> id);
}
