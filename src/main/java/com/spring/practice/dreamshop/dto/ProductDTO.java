package com.spring.practice.dreamshop.dto;

import java.math.BigDecimal;
import java.util.List;
import com.spring.practice.dreamshop.model.Category;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDTO> images;
}
