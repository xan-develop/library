package com.fct.library.service.interfaces;

import java.util.List;

import com.fct.library.model.Category;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category findByName(String name);
    Category create(Category category);
    Category update(Long id, Category category);
    void delete(Long id);
}
