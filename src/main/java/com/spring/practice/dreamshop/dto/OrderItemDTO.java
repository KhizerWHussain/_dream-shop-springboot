package com.spring.practice.dreamshop.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemDTO {
    private ProductDTO product;
    private int quantity;
    private BigDecimal price;
}
