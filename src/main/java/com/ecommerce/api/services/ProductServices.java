package com.ecommerce.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;
import com.ecommerce.api.persistence.interfaces.CrudProduct;
import com.ecommerce.api.persistence.repository.RepositoryProduct;
import com.ecommerce.api.persistence.repository.UpdateRepositoryProduct;

@Service
public class ProductServices implements CrudProduct {
    @Autowired
    private RepositoryProduct repositoryProduct;
    @Autowired
    private UpdateRepositoryProduct updateRepositoryProduct;
    @Override
    public void save(Product product) {
        repositoryProduct.save(product);
    }

    @Override
    public Product findById(String id) {
        return repositoryProduct.findById(id).orElse(null);
    }

    @Override
    public void update(Product product, String id) {
      updateRepositoryProduct.updateProductoPersonalize(id,product);
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
        return repositoryProduct.findByCategories(category);
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
