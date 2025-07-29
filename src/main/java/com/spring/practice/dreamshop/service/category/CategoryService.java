package com.spring.practice.dreamshop.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.practice.dreamshop.exception.AlreadyExistException;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Category;
import com.spring.practice.dreamshop.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository _category;

    @Override
    public Category getById(Long id) {
        return _category.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Override
    public Category getByName(String name) {
        return _category.findByName(name);
    }

    @Override
    public List<Category> get() {
        return _category.findAll();

    }

    @Override
    public Category create(Category category) {
        if (_category.existsByName(category.getName())) {
            throw new AlreadyExistException(category.getName() + " already exists");
        }
        return _category.save(category);
    }

    @Override
    public Category update(Category category, Long id) {
        return Optional.ofNullable(getById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return _category.save(oldCategory);
        }).orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Override
    public void deleteById(Long id) {
        _category.findById(id).ifPresentOrElse(_category::delete, () -> {
            throw new NotFoundException("Category not found");
        });
    }

}
