package com.spring.practice.dreamshop.dto;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class CartDTO {
    private Long id;
    private BigDecimal amount;
    private Set<CartItemDTO> items;
}
