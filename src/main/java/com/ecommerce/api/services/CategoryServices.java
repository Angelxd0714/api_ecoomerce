package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.CategoriesRequest;
import com.ecommerce.api.dto.response.CategoryDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.interfaces.CrudCategory;
import com.ecommerce.api.persistence.repository.RepositoryCategory;

import java.util.List;

@Service
public class CategoryServices implements CrudCategory {
    @Autowired
    private RepositoryCategory repositoryCategory;

    @Override
    public void save(CategoriesRequest category) {
        Category categoryRequest =
                Category.builder()
                        .name(category.getName())
                        .description(category.getDescription())
                        .build();
        repositoryCategory.save(categoryRequest);
    }

    @Override
    public void delete(Long id) {
       repositoryCategory.deleteById(id);
    }

    @Override
    public CategoryDTO getOne(Long id) {

        return CategoryDTO.builder()
                .name(repositoryCategory.findById(id).get().getName())
                .description(repositoryCategory.findById(id).get().getDescription())
                .build();
    }

    @Override
    public List<CategoryDTO> getAll() {

        return repositoryCategory.findAll().stream().map(category -> CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build()).toList();
    }

    @Override
    @Transactional
    public void update(CategoriesRequest category, Long id) {
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

    @Override
    public List<CategoryDTO> getOneByName(String name) {
        return repositoryCategory.findCategoriesByName(name).stream().map(category -> CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build()).toList(
        );
    }


}
