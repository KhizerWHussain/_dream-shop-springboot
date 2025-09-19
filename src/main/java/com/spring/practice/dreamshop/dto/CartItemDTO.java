package com.spring.practice.dreamshop.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private int quantity;
    private BigDecimal unit_price;
    private BigDecimal total_price;
    private ProductDTO product;
}
