package com.spring.practice.dreamshop.controllers;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.practice.dreamshop.dto.OrderDTO;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Order;
import com.spring.practice.dreamshop.response.APIResponse;
import com.spring.practice.dreamshop.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderInterface;

    @PostMapping("/create/{user_id}")
    public ResponseEntity<APIResponse> create(@PathVariable Long user_id) {
        try {
            OrderDTO order = orderInterface.create(user_id);
            return ResponseEntity.ok(new APIResponse(true, "Order places successfully", order));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<APIResponse> read(@PathVariable Long order_id) {
        try {
            OrderDTO order = orderInterface.read(order_id);
            return ResponseEntity.ok().body(new APIResponse(true, "Order found successfully", order));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/of/{user_id}")
    public ResponseEntity<APIResponse> get_user_orders(@PathVariable Long user_id) {
        try {
            List<OrderDTO> orders = orderInterface.getUserOrders(user_id);

            if (orders.isEmpty()) {
                return ResponseEntity.ok().body(new APIResponse(true, "Orders not found", Collections.emptyList()));
            }

            return ResponseEntity.ok().body(new APIResponse(true, "Order found successfully", orders));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

}
