package com.spring.practice.dreamshop.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Product;
import com.spring.practice.dreamshop.request.AddProduct;
import com.spring.practice.dreamshop.request.UpdateProduct;
import com.spring.practice.dreamshop.response.APIResponse;
import com.spring.practice.dreamshop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productInterface;

    @GetMapping("/")
    public ResponseEntity<APIResponse> get() {
        List<Product> products = productInterface.get();

        return ResponseEntity.ok(new APIResponse(true, "Products found sucessfully", products));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<APIResponse> getById(@PathVariable Long id) {
        try {
            Product product = productInterface.read(id);

            return ResponseEntity.ok(new APIResponse(true, "Product detail found successfully", product));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> create(@RequestBody AddProduct product) {
        try {
            Product prod = productInterface.create(product);

            return ResponseEntity.ok(new APIResponse(true, "Product created successfully", prod));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(false, e.getMessage(), null));
        }

    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> update(@RequestBody UpdateProduct payload) {
        try {
            Product product = productInterface.update(payload, payload.getId());
            return ResponseEntity.ok(new APIResponse(true, "Product updated successfully", product));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> delete(@PathVariable Long id) {
        try {
            productInterface.delete(id);

            return ResponseEntity.ok(new APIResponse(true, "Product deleted successfully", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-product-and-brand/{brand}/{name}")
    public ResponseEntity<APIResponse> getByBrandName(@PathVariable String brand,
            @PathVariable String name) {
        try {
            List<Product> product = productInterface.getByBrandAndName(brand, name);

            if (product.isEmpty()) {
                return ResponseEntity.ok(new APIResponse(false, "Products not found successfully", null));
            }

            return ResponseEntity.ok(new APIResponse(true, "Products found successfully", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-category-and-brand/{category}/{brand}")
    public ResponseEntity<APIResponse> getByCategoryAndBrand(@PathVariable String category,
            @PathVariable String brand) {
        try {
            List<Product> product = productInterface.getByCategoryAndBrand(category, brand);

            if (product.isEmpty()) {
                return ResponseEntity.ok(new APIResponse(false, "Products not found successfully", null));
            }

            return ResponseEntity.ok(new APIResponse(true, "Products found successfully", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<APIResponse> getByName(@PathVariable String name) {
        try {
            List<Product> product = productInterface.getByName(name);

            if (product.isEmpty()) {
                return ResponseEntity.ok(new APIResponse(false, "Products not found successfully", null));
            }

            return ResponseEntity.ok(new APIResponse(true, "Products found successfully", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-brand/{brand}")
    public ResponseEntity<APIResponse> getByBrand(@PathVariable String brand) {
        try {
            List<Product> product = productInterface.getByBrand(brand);

            if (product.isEmpty()) {
                return ResponseEntity.ok(new APIResponse(false, "Products not found successfully", null));
            }

            return ResponseEntity.ok(new APIResponse(true, "Products found successfully", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/get-by-category/{category}")
    public ResponseEntity<APIResponse> getByCategory(@PathVariable String category) {
        try {
            List<Product> product = productInterface.getByCategory(category);

            if (product.isEmpty()) {
                return ResponseEntity.ok(new APIResponse(false, "Products not found successfully", null));
            }

            return ResponseEntity.ok(new APIResponse(true, "Products found successfully", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/get-count-by-brand-and-name/{brand}/{name}")
    public ResponseEntity<APIResponse> countByBrandAndName(@PathVariable String brand, @PathVariable String name) {
        try {
            Long count = productInterface.countByBrandAndName(brand, name);

            return ResponseEntity.ok(new APIResponse(true, "Products count found successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new APIResponse(false, e.getMessage(), null));
        }
    }

}
