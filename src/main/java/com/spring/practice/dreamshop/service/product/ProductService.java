package com.spring.practice.dreamshop.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.practice.dreamshop.exception.ProductNotFoundException;
import com.spring.practice.dreamshop.model.Category;
import com.spring.practice.dreamshop.model.Product;
import com.spring.practice.dreamshop.repository.CategoryRepository;
import com.spring.practice.dreamshop.repository.ProductRepository;
import com.spring.practice.dreamshop.request.AddProduct;
import com.spring.practice.dreamshop.request.UpdateProduct;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository _product;
    private final CategoryRepository _category;

    @Override
    public Product create(AddProduct request) {
        Category category = Optional.ofNullable(_category.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return _category.save(newCategory);
                });

        request.setCategory(category);

        return _product.save(insertOne(request, category));
    }

    private Product insertOne(AddProduct request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category);
    }

    @Override
    public List<Product> get() {
        return _product.findAll();
    }

    @Override
    public Product read(Long id) {
        return _product.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void delete(Long id) {
        _product.findById(id).ifPresentOrElse(_product::delete, () -> {
            throw new ProductNotFoundException("Product not found");
        });

    }

    @Override
    public Product update(UpdateProduct request, Long product_id) {
        return _product.findById(product_id)
                .map(existingProduct -> updateOne(existingProduct, request))
                .map(_product::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Product updateOne(Product product, UpdateProduct request) {
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());
        product.setDescription(request.getDescription());

        Category category = _category.findByName(request.getCategory().getName());
        product.setCategory(category);

        return product;
    }

    @Override
    public List<Product> getByCategory(String category) {
        return _product.findByCategoryName(category);
    }

    @Override
    public List<Product> getByBrand(String brand) {
        return _product.findByBrandName(brand);
    }

    @Override
    public List<Product> getByCategoryAndBrand(String category, String brand) {
        return _product.findByCategoryAndBrand(category, brand);
    }

    @Override
    public List<Product> getByName(String name) {
        return _product.findByName(name);
    }

    @Override
    public List<Product> getByBrandAndName(String brand, String name) {
        return _product.findByBrandAndName(brand, name);

    }

    @Override
    public Long countByBrandAndName(String brand, String name) {
        return _product.countByBrandAndName(brand, name);
    }
}