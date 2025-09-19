package com.spring.practice.dreamshop.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Long product_id;
    private String product_name;
    private int quantity;
    private BigDecimal price;
}
