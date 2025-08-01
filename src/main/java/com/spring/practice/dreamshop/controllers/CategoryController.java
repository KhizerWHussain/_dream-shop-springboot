package com.spring.practice.dreamshop.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.practice.dreamshop.model.Category;
import com.spring.practice.dreamshop.response.APIResponse;
import com.spring.practice.dreamshop.service.category.CategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public ResponseEntity<APIResponse> get() {
        List<Category> categories = categoryService.get();
        return ResponseEntity.ok(new APIResponse(true, "categories found successfully", categories));
    }

}
