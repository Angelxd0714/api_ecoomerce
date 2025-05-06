package com.ecommerce.api.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.CarDTO;
import com.ecommerce.api.dto.response.CategoryDTO;
import com.ecommerce.api.dto.response.MarkersDTO;
import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.repository.RepositoryCategory;
import com.ecommerce.api.persistence.repository.RepositoryMarkers;
import com.ecommerce.api.utils.UploadImages;
import com.ecommerce.api.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;
import com.ecommerce.api.persistence.interfaces.CrudProduct;
import com.ecommerce.api.persistence.repository.RepositoryProduct;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ProductServices implements CrudProduct {
        @Autowired
        private RepositoryProduct repositoryProduct;
        @Autowired
        private RepositoryMarkers repositoryMarkers;
        @Autowired
        private RepositoryCategory repositoryCategory;

        @Autowired
        private UploadImages uploadFile;

        @Transactional
        @Override
        public void save(ProductRequest product, MultipartFile file) throws IOException {
                try {

                        String url = uploadFile.uploadImage(file);

                        List<Long> categoriesIds = product.getCategoriesRequestLis().stream()
                                        .map(CategoriesRequest -> CategoriesRequest.getId())
                                        .toList();
                        Iterable<Category> categoriesId = repositoryCategory.findAllById(categoriesIds);
                        List<Category> categories = new ArrayList<>();
                        categoriesId.forEach(categories::add);
                        log.info("Categories: {}", categories);
                        Markers marker = repositoryMarkers.findById(product.getMarker().getId())
                                        .orElseGet(() -> repositoryMarkers.save(Markers.builder()
                                                        .name(product.getMarker().getName())
                                                        .description(product.getMarker().getDescription())
                                                        .build()));
                        log.info("Marker: {}", marker.getId());

                        // Vincular al producto
                        Product product1 = Product.builder()
                                        .name(product.getName())
                                        .description(product.getDescription())
                                        .price(product.getPrice())
                                        .stock(product.getStock())
                                        .image(url)
                                        .categories(categories)
                                        .marker(marker)
                                        .build();

                        repositoryProduct.save(product1);
                } catch (Exception e) {
                        log.error("Error al guardar el producto", e);
                        throw new RuntimeException(e);

                }
        }

        @Override
        public ProductDTO findById(Long id) {

                if (id == null) {
                        throw new RuntimeException("Producto no encontrado");
                }
                if (id <= 0) {
                        throw new RuntimeException("Invalido el Id");
                }
                if (!repositoryProduct.existsById(id)) {
                        throw new RuntimeException("Producto no encontrado");
                }
                if (repositoryProduct.findById(id).isEmpty()) {
                        throw new RuntimeException("Producto no encontrado");
                }

                return repositoryProduct.findById(id).map(product -> ProductDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .image(product.getImage())
                                .categories(product.getCategories().stream().map(category -> CategoryDTO.builder()
                                                .id(category.getId())
                                                .name(category.getName())
                                                .description(category.getDescription())
                                                .build()).toList())
                                .marker(MarkersDTO.builder()
                                                .id(product.getMarker().getId())
                                                .name(product.getMarker().getName())
                                                .description(product.getMarker().getDescription())
                                                .build())
                                .createdAt(product.getCreatedAt())
                                .updatedAt(product.getUpdatedAt())
                                .build()).orElseThrow(() -> new RuntimeException("Product not found"));
        }

        @Override
        @Transactional
        public void update(ProductRequest productRequest, Long id, MultipartFile file) throws IOException {

                Product existingProduct = repositoryProduct.findById(id)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                // 2. Actualizar campos básicos
                existingProduct.setName(productRequest.getName());
                existingProduct.setDescription(productRequest.getDescription());
                existingProduct.setPrice(productRequest.getPrice());
                existingProduct.setStock(productRequest.getStock());

                // 3. Manejo de imagen (solo si hay archivo nuevo)
                if (file != null && !file.isEmpty()) {
                        String url = uploadFile.uploadImage(file);
                        existingProduct.setImage(url);
                        Files.delete(Path.of(url));
                }

                // 4. Actualizar categorías (evitar duplicados)
                List<Category> categories = productRequest.getCategoriesRequestLis().stream()
                                .map(categoryRequest -> Category.builder()
                                                .name(categoryRequest.getName())
                                                .description(categoryRequest.getDescription())
                                                .build())
                                .toList();
                existingProduct.setCategories(categories);

                // 5. Actualizar marker (si aplica)
                if (productRequest.getMarker() != null) {
                        Markers existingMarker = repositoryMarkers.findByName(productRequest.getMarker().getName())
                                        .orElseGet(() -> repositoryMarkers.save(Markers.builder()
                                                        .name(productRequest.getMarker().getName())
                                                        .description(productRequest.getMarker().getDescription())
                                                        .build()));
                        existingProduct.setMarker(existingMarker);
                }
                log.info("Product updated: {}", existingProduct.getMarker().getName());

                // 6. Guardar cambios (JPA actualiza automáticamente)
                repositoryProduct.updateProductoPersonalize(id, existingProduct);
        }

        @Override
        public void delete(Long id) {
                repositoryProduct.deleteById(id);
        }

        @Override
        public List<ProductDTO> findAll() {

                List<Product> products = repositoryProduct.findAll();

                return products.stream().map(product -> ProductDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .image(product.getImage())
                                .categories(product.getCategories().stream().map(category -> CategoryDTO.builder()
                                                .id(category.getId())
                                                .name(category.getName())
                                                .description(category.getDescription())
                                                .build()).toList())
                                .marker(MarkersDTO.builder()
                                                .id(product.getMarker().getId())
                                                .name(product.getMarker().getName())
                                                .description(product.getMarker().getDescription())
                                                .build())
                                .createdAt(product.getCreatedAt())
                                .updatedAt(product.getUpdatedAt())
                                .build()).toList();
        }

        @Override
        public List<ProductDTO> findByName(String name) {

                return repositoryProduct.findByName(List.of(name)).stream().map(product -> ProductDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .image(product.getImage())
                                .categories(product.getCategories().stream().map(category -> CategoryDTO.builder()
                                                .id(category.getId())
                                                .name(category.getName())
                                                .description(category.getDescription())
                                                .build()).toList())
                                .marker(MarkersDTO.builder()
                                                .id(product.getMarker().getId())
                                                .name(product.getMarker().getName())
                                                .description(product.getMarker().getDescription())
                                                .build())
                                .createdAt(product.getCreatedAt())
                                .updatedAt(product.getUpdatedAt())
                                .build()).toList();
        }

        @Override
        public List<ProductDTO> findByCategory(String category) {
                List<Category> categories = repositoryCategory.findCategoriesByName(category);
                return repositoryProduct.findByCategories(categories).stream().map(product -> ProductDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .image(product.getImage())
                                .categories(product.getCategories().stream().map(category1 -> CategoryDTO.builder()
                                                .id(category1.getId())
                                                .name(category1.getName())
                                                .description(category1.getDescription())
                                                .build()).toList())
                                .marker(MarkersDTO.builder()
                                                .id(product.getMarker().getId())
                                                .name(product.getMarker().getName())
                                                .description(product.getMarker().getDescription())
                                                .build())
                                .createdAt(product.getCreatedAt())
                                .updatedAt(product.getUpdatedAt())
                                .build()).toList();
        }

        @Override
        public List<ProductDTO> findByMarker(String marker) {
                return repositoryProduct.findByMarker(marker).stream().map(product -> ProductDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .image(product.getImage())
                                .categories(product.getCategories().stream().map(category -> CategoryDTO.builder()
                                                .id(category.getId())
                                                .name(category.getName())
                                                .description(category.getDescription())
                                                .build()).toList())
                                .marker(MarkersDTO.builder()
                                                .id(product.getMarker().getId())
                                                .name(product.getMarker().getName())
                                                .description(product.getMarker().getDescription())
                                                .build())
                                .createdAt(product.getCreatedAt())
                                .updatedAt(product.getUpdatedAt())
                                .build()).toList();
        }

        @Override
        public List<ProductDTO> findByPrice(BigDecimal price) {
                return repositoryProduct.findByPrice(price).stream().map(product -> ProductDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .image(product.getImage())
                                .categories(product.getCategories().stream().map(category -> CategoryDTO.builder()
                                                .id(category.getId())
                                                .name(category.getName())
                                                .description(category.getDescription())
                                                .build()).toList())
                                .marker(MarkersDTO.builder()
                                                .id(product.getMarker().getId())
                                                .name(product.getMarker().getName())
                                                .description(product.getMarker().getDescription())
                                                .build())
                                .createdAt(product.getCreatedAt())
                                .updatedAt(product.getUpdatedAt())
                                .build()).toList();

        }

        @Override
        public List<ProductDTO> findByIds(List<Long> id) {
                return repositoryProduct.findByIds(id).stream().map(product -> ProductDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .image(product.getImage())
                                .categories(product.getCategories().stream().map(category -> CategoryDTO.builder()
                                                .id(category.getId())
                                                .name(category.getName())
                                                .description(category.getDescription())
                                                .build()).toList())
                                .marker(MarkersDTO.builder()
                                                .id(product.getMarker().getId())
                                                .name(product.getMarker().getName())
                                                .description(product.getMarker().getDescription())
                                                .build())
                                .createdAt(product.getCreatedAt())
                                .updatedAt(product.getUpdatedAt())
                                .build()).toList();
        }

        @Transactional
        public void updateProduct(Long orderId) {
                Orders orders = Orders.builder()
                                .id(orderId)
                                .build();
                List<Product> products = repositoryProduct.findAll().stream().filter(x -> x.getOrder().equals(orders))
                                .toList();
                products.forEach(x -> {
                        x.setStock(x.getStock() - 1);
                        repositoryProduct.updateProductoPersonalize(x.getId(), x);
                });

        }

}
