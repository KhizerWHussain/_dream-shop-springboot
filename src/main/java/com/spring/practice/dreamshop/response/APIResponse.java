package com.spring.practice.dreamshop.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse {
    private Boolean status;
    private String message;
    private Object data;
}
