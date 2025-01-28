package com.ecommerce.api.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ecommerce.api.dto.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;
import com.ecommerce.api.persistence.interfaces.CrudProduct;
import com.ecommerce.api.persistence.repository.RepositoryProduct;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServices implements CrudProduct {
    @Autowired
    private RepositoryProduct repositoryProduct;


    @Autowired
    private S3Service s3Service;
    @Override
    public void save(ProductRequest product, MultipartFile file) throws IOException {

        String url = s3Service.uploadFile(file.getOriginalFilename(), new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename()));



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

        repositoryProduct.save(product1);
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
        String url = s3Service.uploadFile(file.getOriginalFilename(), tempFile);

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
    public Iterable<Product> findAll() {
        return repositoryProduct.findAll();
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
