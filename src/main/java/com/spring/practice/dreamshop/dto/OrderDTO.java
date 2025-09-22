package com.spring.practice.dreamshop.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import com.spring.practice.dreamshop.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private Long user_id;
    private LocalDate datetime;
    private BigDecimal amount;
    private OrderStatus status;
    private Set<OrderItemDTO> items;
}
