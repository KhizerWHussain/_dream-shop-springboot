package com.spring.practice.dreamshop.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private List<OrderDTO> orders;
    private CartDTO cart;
}
