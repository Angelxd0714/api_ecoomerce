package com.ecommerce.api.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.CategoryDTO;
import com.ecommerce.api.dto.response.MarkersDTO;
import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.persistence.repository.RepositoryMarkers;
import com.ecommerce.api.utils.Utils;
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
    private RepositoryMarkers  repositoryMarkers;


    @Autowired
    private StorageService s3Service;
    @Override
    public void save(ProductRequest product, MultipartFile file) throws IOException {
        try {
            Utils utils = new Utils();
            // 1. Use a system-provided temporary file (best practice for temporary files)
            Path tempFile = Files.createTempFile("product-image", "." + utils.getFileExtension(file.getOriginalFilename())); // Unique prefix

            // 2. Transfer the MultipartFile content to the temporary file
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            String name = file.getOriginalFilename() + "-" + System.currentTimeMillis();

            // 3. Upload to S3 using the temporary file
            String url = s3Service.uploadFile(name, tempFile.toFile());  // Pass the File object";

            // 4. (Important!) Delete the temporary file after upload
            Files.delete(tempFile); // Ensure cleanup


            List<Category> category =
                    product.getCategoriesRequestLis().stream().map(categoryRequest -> Category.builder()
                            .name(categoryRequest.getName())
                            .description(categoryRequest.getDescription())
                            .build()).toList();
            log.info("category: {}", category);
            Markers marker = Markers.builder()
                    .name(product.getMarker().getName())
                    .description(product.getMarker().getDescription())
                    .build();
            repositoryMarkers.save(marker);

            Product product1 = Product.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .image(url)
                    .categories(category)
                    .marker(marker)
                    .build();
            log.info("product: {}",category);

            repositoryProduct.save(product1);
        } catch (Exception e) {
            log.error("Error al guardar el producto", e);
            throw new RuntimeException(e);

        }
    }



    @Override
    public Product findById(String id) {
        return repositoryProduct.findById(id).orElse(null);
    }

    @Override
    public void update(ProductRequest product,String id,MultipartFile file) throws IOException {
        repositoryProduct.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));


        File tempFile = null;
        if (file != null) {
            tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        }

        file.transferTo(tempFile);
        String url = "s3Service.uploadFile(file.getOriginalFilename(), tempFile);";

        List<Category> category =
                product.getCategoriesRequestLis().stream().map(categoryRequest -> Category.builder()
                        .name(categoryRequest.getName())
                        .description(categoryRequest.getDescription())
                        .build()).toList();
        Markers marker = Markers.builder()
                .name(product.getMarker().getName())
                .description(product.getMarker().getDescription())
                .build();

        Product product1 = Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .image(url)
                .categories(category)
                .marker(marker)
                .build();
      repositoryProduct.updateProductoPersonalize(Long.valueOf(id),product1);
    }

    @Override
    public void delete(String id) {
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
                .categories(product.getCategories().stream().map(category ->
                 CategoryDTO.builder()
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
    public Iterable<Product> findByName(List<String> name) {
       return repositoryProduct.findByName(name);
    }

    @Override
    public Iterable<Product> findByCategory(List<String> category) {
        List<Category> categoryList = category.stream().map(category1 -> Category.builder()
                .name(category1)
                .build()).toList();
        return repositoryProduct.findByCategories(categoryList);
    }

    @Override
    public Iterable<Product> findByMarker(List<String> marker) {
       return repositoryProduct.findByMarker(marker);
    }

    @Override
    public Iterable<Product> findByPrice(List<Double> price) {
        return repositoryProduct.findByPrice(price);
    }
    
}
