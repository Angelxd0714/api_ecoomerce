package com.ecommerce.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.interfaces.CrudCategory;
import com.ecommerce.api.persistence.repository.RepositoryCategory;
import com.ecommerce.api.persistence.repository.UpdateRepositoryPersonalize;

@Service
public class CategoryServices implements CrudCategory {
    @Autowired
    private RepositoryCategory repositoryCategory;
    @Autowired
    private UpdateRepositoryPersonalize categoryUpdate;
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
    public void update(Category category, String id) {
      categoryUpdate.updateCategory(id, category);

    }
    
}
