package com.spring.practice.dreamshop.repository;

import com.spring.practice.dreamshop.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_Name(String categoryName);

    List<Product> findByBrand(String brand);

    List<Product> findByCategory_NameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);

    Boolean existsByNameAndBrand(String name, String brand);

}
