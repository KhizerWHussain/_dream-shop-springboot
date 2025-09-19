package com.spring.practice.dreamshop.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String first_name;
    private String last_name;
    private String email;
    private String password;
}
