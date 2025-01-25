package com.ecommerce.api.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.interfaces.CrudCategory;
import com.ecommerce.api.persistence.repository.RepositoryCategory;

@Service
public class CategoryServices implements CrudCategory {
    @Autowired
    private RepositoryCategory repositoryCategory;

    @Override
    public void save(Category category) {
        repositoryCategory.save(category);
    }

    @Override
    public void delete(String id) {
       repositoryCategory.deleteById(id);
    }

    @Override
    public Category getOne(String id) {
        return repositoryCategory.findById(id).orElse(null);
    }

    @Override
    public Iterable<Category> getAll() {
        return repositoryCategory.findAll();
    }

    @Override
    @Transactional
    public void update(Category category, String id) {
        Category existingCategory = repositoryCategory.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        try {
            if (category.getName() != null && !category.getName().trim().isEmpty()) {
                repositoryCategory.updateCategory(id, category.getName());
            } else {
                throw new IllegalArgumentException("Category name cannot be empty");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating category: " + e.getMessage());
        }
    }


}
