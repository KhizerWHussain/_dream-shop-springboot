package com.spring.practice.dreamshop.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.practice.dreamshop.exception.AlreadyExistException;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Category;
import com.spring.practice.dreamshop.response.APIResponse;
import com.spring.practice.dreamshop.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryInterface;

    @GetMapping
    public ResponseEntity<APIResponse> get() {
        try {
            List<Category> categories = categoryInterface.get();
            return ResponseEntity.ok(new APIResponse(true, "categories found successfully", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(false, "Internal server error", null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> create(@RequestBody Category name) {
        try {
            Category category = categoryInterface.create(name);

            return ResponseEntity.ok(new APIResponse(true, "Category created successfully", category.getId()));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<APIResponse> getById(@PathVariable Long id) {
        try {
            Category category = categoryInterface.getById(id);

            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse(false, "Category not found", null));
            }

            return ResponseEntity.ok(new APIResponse(true, "Category found successfully", category));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/category/by/{name}")
    public ResponseEntity<APIResponse> getByName(@PathVariable String name) {
        try {
            Category category = categoryInterface.getByName(name);

            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse(false, "Category not found", null));
            }

            return ResponseEntity.ok(new APIResponse(true, "Category found successfully", category));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<APIResponse> delete(@PathVariable Long id) {
        try {
            categoryInterface.deleteById(id);
            return ResponseEntity.ok(new APIResponse(true, "Category deleted successfully", null));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse> update(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryInterface.update(category, id);
            return ResponseEntity.ok(new APIResponse(true, "Category updated successfully", updatedCategory));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

}
