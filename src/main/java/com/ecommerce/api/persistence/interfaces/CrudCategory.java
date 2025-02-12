package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.dto.request.CategoriesRequest;
import com.ecommerce.api.dto.response.CategoryDTO;
import com.ecommerce.api.persistence.entities.Category;

import java.util.List;

public interface CrudCategory {

    void save(CategoriesRequest category);

    void delete(Long id);

    CategoryDTO getOne(Long id);

    List<CategoryDTO> getAll();

    void update(CategoriesRequest category,Long id);

    List<CategoryDTO> getOneByName(String name);
}
