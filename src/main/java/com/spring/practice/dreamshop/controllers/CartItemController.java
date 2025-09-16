package com.spring.practice.dreamshop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.response.APIResponse;
import com.spring.practice.dreamshop.service.carts.ICartItemService;
import com.spring.practice.dreamshop.service.carts.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {
    private final ICartItemService cartItemInterface;
    private final ICartService cartInterface;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> add(
            @RequestParam(required = false) Long cart_id,
            @RequestParam Long product_id,
            @RequestParam int quantity) {

        try {

            if (cart_id == null) {
                cart_id = cartInterface.init();
            }

            cartItemInterface.add(cart_id, product_id, quantity);
            return ResponseEntity.ok(new APIResponse(true, "Item added to cart successfully", product_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/item/{cart_id}/{product_id}")
    public ResponseEntity<APIResponse> remove(@PathVariable Long cart_id, @PathVariable Long product_id) {
        try {
            cartItemInterface.remove(cart_id, product_id);
            return ResponseEntity.ok(new APIResponse(true, "Item removed successfully", null));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/item/update-quantity/{cart_id}/{product_id}")
    public ResponseEntity<APIResponse> updateQuantity(
            @PathVariable Long cart_id,
            @PathVariable Long product_id,
            @RequestParam int quantity) {

        try {
            cartItemInterface.updateQuantity(cart_id, product_id, quantity);
            return ResponseEntity.ok(new APIResponse(true, "Item quantity updated successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }

    }
}
