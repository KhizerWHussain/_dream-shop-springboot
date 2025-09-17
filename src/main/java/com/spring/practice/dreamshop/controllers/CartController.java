package com.spring.practice.dreamshop.controllers;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Cart;
import com.spring.practice.dreamshop.response.APIResponse;
import com.spring.practice.dreamshop.service.carts.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartInterface;

    @GetMapping("/cart/{id}")
    public ResponseEntity<APIResponse> read(@PathVariable Long id) {
        try {
            Cart cart = cartInterface.read(id);

            return ResponseEntity.ok().body(new APIResponse(true, "Cart found successfully", cart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<APIResponse> clear(@PathVariable Long id) {
        try {
            cartInterface.clear(id);
            return ResponseEntity.ok(new APIResponse(true, "Cart deleted successfully", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/cart/{id}/amount")
    public ResponseEntity<APIResponse> getAmount(@PathVariable Long id) {
        try {
            BigDecimal price = cartInterface.getTotal_Price(id);
            return ResponseEntity.ok(new APIResponse(true, "Cart total amount found successfully", price));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }
}
