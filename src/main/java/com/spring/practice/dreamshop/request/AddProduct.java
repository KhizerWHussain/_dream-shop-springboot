package com.spring.practice.dreamshop.request;

import java.math.BigDecimal;

import com.spring.practice.dreamshop.model.Category;

import lombok.Data;

@Data
public class AddProduct {
    private Long id;
    private String name;
    private String brand;

    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
