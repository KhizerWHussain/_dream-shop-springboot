package com.spring.practice.dreamshop.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private Long id;
    private String first_name;
    private String last_name;
}
