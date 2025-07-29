package com.spring.practice.dreamshop.service.category;

import java.util.List;

import com.spring.practice.dreamshop.model.Category;

public interface ICategoryService {
    Category getById(Long id);

    Category getByName(String name);

    List<Category> get();

    Category create(Category category);

    Category update(Category category, Long id);

    void deleteById(Long id);
}
